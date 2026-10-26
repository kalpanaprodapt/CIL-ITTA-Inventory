package com.uniti.cil.itta.inventory.vendor.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.event.SupportContractEvents;
import com.uniti.cil.itta.inventory.vendor.mapper.SupportContractMapper;
import com.uniti.cil.itta.inventory.vendor.model.SupportContract;
import com.uniti.cil.itta.inventory.vendor.repository.SupportContractRepository;
import com.uniti.cil.itta.inventory.vendor.spec.SupportContractSpecifications;
import com.uniti.cil.itta.inventory.vendor.validation.SupportContractValidator;
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
public class SupportContractServiceImpl implements SupportContractService {

    private static final Logger log = LoggerFactory.getLogger(SupportContractServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SupportContractRepository repository;
    private final SupportContractMapper mapper;
    private final SupportContractValidator validator;
    private final EventPublisher events;

    public SupportContractServiceImpl(SupportContractRepository repository, SupportContractMapper mapper, SupportContractValidator validator,
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

    private SupportContract load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("SupportContract", id));
    }

    @Override
    public SupportContractResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SupportContractResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("SupportContract", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SupportContractResponse> search(SupportContractSearchCriteria criteria, Pageable pageable) {
        Page<SupportContract> page = repository.findAll(SupportContractSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SupportContractSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SupportContractResponse create(SupportContractCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("SupportContract", "code", request.getCode());
        }
        SupportContract entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        SupportContract saved = repository.save(entity);
        SupportContractResponse response = mapper.toResponse(saved);
        events.publish(SupportContractEvents.created(response, actor()));
        log.info("Created SupportContract id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SupportContractResponse update(UUID id, SupportContractUpdateRequest request) {
        SupportContract entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "SupportContract " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("SupportContract", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        SupportContract saved = repository.save(entity);
        SupportContractResponse response = mapper.toResponse(saved);
        events.publish(SupportContractEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SupportContractEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SupportContractResponse changeStatus(UUID id, LifecycleStatus status) {
        SupportContract entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        SupportContract saved = repository.save(entity);
        SupportContractResponse response = mapper.toResponse(saved);
        events.publish(SupportContractEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SupportContract entity = load(id);
        SupportContractResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SupportContractEvents.deleted(snapshot, actor()));
        log.info("Deleted SupportContract id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SupportContractResponse> bulkCreate(List<SupportContractCreateRequest> requests) {
        BulkResult<SupportContractResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of SupportContract failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SupportContractResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SupportContractResponse> result = new BulkResult<>();
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
    public void exportCsv(SupportContractSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SupportContractMapper.CSV_HEADER));
        int page = 0;
        Page<SupportContract> chunk;
        do {
            chunk = repository.findAll(SupportContractSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SupportContractResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
