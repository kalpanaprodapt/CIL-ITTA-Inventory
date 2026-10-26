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
import com.uniti.cil.itta.inventory.assetmgmt.event.InvoiceEvents;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.InvoiceMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.Invoice;
import com.uniti.cil.itta.inventory.assetmgmt.repository.InvoiceRepository;
import com.uniti.cil.itta.inventory.assetmgmt.spec.InvoiceSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.validation.InvoiceValidator;
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
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;
    private final InvoiceValidator validator;
    private final EventPublisher events;

    public InvoiceServiceImpl(InvoiceRepository repository, InvoiceMapper mapper, InvoiceValidator validator,
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

    private Invoice load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
    }

    @Override
    public InvoiceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public InvoiceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<InvoiceResponse> search(InvoiceSearchCriteria criteria, Pageable pageable) {
        Page<Invoice> page = repository.findAll(InvoiceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<InvoiceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public InvoiceResponse create(InvoiceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Invoice", "code", request.getCode());
        }
        Invoice entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Invoice saved = repository.save(entity);
        InvoiceResponse response = mapper.toResponse(saved);
        events.publish(InvoiceEvents.created(response, actor()));
        log.info("Created Invoice id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public InvoiceResponse update(UUID id, InvoiceUpdateRequest request) {
        Invoice entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Invoice " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Invoice", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Invoice saved = repository.save(entity);
        InvoiceResponse response = mapper.toResponse(saved);
        events.publish(InvoiceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(InvoiceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public InvoiceResponse changeStatus(UUID id, LifecycleStatus status) {
        Invoice entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Invoice saved = repository.save(entity);
        InvoiceResponse response = mapper.toResponse(saved);
        events.publish(InvoiceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Invoice entity = load(id);
        InvoiceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(InvoiceEvents.deleted(snapshot, actor()));
        log.info("Deleted Invoice id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<InvoiceResponse> bulkCreate(List<InvoiceCreateRequest> requests) {
        BulkResult<InvoiceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Invoice failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<InvoiceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<InvoiceResponse> result = new BulkResult<>();
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
    public void exportCsv(InvoiceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(InvoiceMapper.CSV_HEADER));
        int page = 0;
        Page<Invoice> chunk;
        do {
            chunk = repository.findAll(InvoiceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<InvoiceResponse> findByPoId(UUID poId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPoId(tenant(), poId, pageable).map(mapper::toResponse));
    }
}
