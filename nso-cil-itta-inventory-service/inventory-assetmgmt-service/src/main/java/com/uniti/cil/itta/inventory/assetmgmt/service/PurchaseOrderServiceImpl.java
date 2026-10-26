package com.uniti.cil.itta.inventory.assetmgmt.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.event.PurchaseOrderEvents;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.PurchaseOrderMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.PurchaseOrder;
import com.uniti.cil.itta.inventory.assetmgmt.repository.PurchaseOrderRepository;
import com.uniti.cil.itta.inventory.assetmgmt.spec.PurchaseOrderSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.validation.PurchaseOrderValidator;
import java.io.Writer;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PurchaseOrderRepository repository;
    private final PurchaseOrderMapper mapper;
    private final PurchaseOrderValidator validator;
    private final EventPublisher events;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository, PurchaseOrderMapper mapper, PurchaseOrderValidator validator,
                          EventPublisher events) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.events = events;
    }

    private String tenant() {
        return TenantContext.requireTenant();
    }

    private String actor() {
        return TenantContext.currentActor();
    }

    private PurchaseOrder load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", id));
    }

    @Override
    public PurchaseOrderResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PurchaseOrderResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PurchaseOrderResponse> search(PurchaseOrderSearchCriteria criteria, Pageable pageable) {
        Page<PurchaseOrder> page = repository.findAll(PurchaseOrderSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PurchaseOrderSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PurchaseOrderResponse create(PurchaseOrderCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PurchaseOrder", "code", request.getCode());
        }
        PurchaseOrder entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PurchaseOrder saved = repository.save(entity);
        PurchaseOrderResponse response = mapper.toResponse(saved);
        events.publish(PurchaseOrderEvents.created(response, actor()));
        log.info("Created PurchaseOrder id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PurchaseOrderResponse update(UUID id, PurchaseOrderUpdateRequest request) {
        PurchaseOrder entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PurchaseOrder " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PurchaseOrder", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PurchaseOrder saved = repository.save(entity);
        PurchaseOrderResponse response = mapper.toResponse(saved);
        events.publish(PurchaseOrderEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PurchaseOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PurchaseOrderResponse changeStatus(UUID id, LifecycleStatus status) {
        PurchaseOrder entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PurchaseOrder saved = repository.save(entity);
        PurchaseOrderResponse response = mapper.toResponse(saved);
        events.publish(PurchaseOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PurchaseOrder entity = load(id);
        PurchaseOrderResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PurchaseOrderEvents.deleted(snapshot, actor()));
        log.info("Deleted PurchaseOrder id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PurchaseOrderResponse> bulkCreate(List<PurchaseOrderCreateRequest> requests) {
        BulkResult<PurchaseOrderResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PurchaseOrder failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PurchaseOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PurchaseOrderResponse> result = new BulkResult<>();
        for (int i = 0; i < ids.size(); i++) {
            try {
                result.addSuccess(changeStatus(ids.get(i), status));
            } catch (RuntimeException ex) {
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public Map<LifecycleStatus, Long> countByStatus() {
        Map<LifecycleStatus, Long> counts = new EnumMap<>(LifecycleStatus.class);
        for (LifecycleStatus s : LifecycleStatus.values()) {
            counts.put(s, 0L);
        }
        for (Object[] row : repository.countGroupedByStatus(tenant())) {
            counts.put((LifecycleStatus) row[0], (Long) row[1]);
        }
        return counts;
    }

    @Override
    public void exportCsv(PurchaseOrderSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PurchaseOrderMapper.CSV_HEADER));
        int page = 0;
        Page<PurchaseOrder> chunk;
        do {
            chunk = repository.findAll(PurchaseOrderSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PurchaseOrderResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
