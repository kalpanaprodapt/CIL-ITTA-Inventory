package com.uniti.cil.itta.inventory.compliance.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.event.AuditRecordEvents;
import com.uniti.cil.itta.inventory.compliance.mapper.AuditRecordMapper;
import com.uniti.cil.itta.inventory.compliance.model.AuditRecord;
import com.uniti.cil.itta.inventory.compliance.repository.AuditRecordRepository;
import com.uniti.cil.itta.inventory.compliance.spec.AuditRecordSpecifications;
import com.uniti.cil.itta.inventory.compliance.validation.AuditRecordValidator;
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
public class AuditRecordServiceImpl implements AuditRecordService {

    private static final Logger log = LoggerFactory.getLogger(AuditRecordServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AuditRecordRepository repository;
    private final AuditRecordMapper mapper;
    private final AuditRecordValidator validator;
    private final EventPublisher events;

    public AuditRecordServiceImpl(AuditRecordRepository repository, AuditRecordMapper mapper, AuditRecordValidator validator,
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

    private AuditRecord load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditRecord", id));
    }

    @Override
    public AuditRecordResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AuditRecordResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("AuditRecord", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AuditRecordResponse> search(AuditRecordSearchCriteria criteria, Pageable pageable) {
        Page<AuditRecord> page = repository.findAll(AuditRecordSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AuditRecordSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AuditRecordResponse create(AuditRecordCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("AuditRecord", "code", request.getCode());
        }
        AuditRecord entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        AuditRecord saved = repository.save(entity);
        AuditRecordResponse response = mapper.toResponse(saved);
        events.publish(AuditRecordEvents.created(response, actor()));
        log.info("Created AuditRecord id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AuditRecordResponse update(UUID id, AuditRecordUpdateRequest request) {
        AuditRecord entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "AuditRecord " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("AuditRecord", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        AuditRecord saved = repository.save(entity);
        AuditRecordResponse response = mapper.toResponse(saved);
        events.publish(AuditRecordEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AuditRecordEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AuditRecordResponse changeStatus(UUID id, LifecycleStatus status) {
        AuditRecord entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        AuditRecord saved = repository.save(entity);
        AuditRecordResponse response = mapper.toResponse(saved);
        events.publish(AuditRecordEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AuditRecord entity = load(id);
        AuditRecordResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AuditRecordEvents.deleted(snapshot, actor()));
        log.info("Deleted AuditRecord id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AuditRecordResponse> bulkCreate(List<AuditRecordCreateRequest> requests) {
        BulkResult<AuditRecordResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of AuditRecord failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AuditRecordResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AuditRecordResponse> result = new BulkResult<>();
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
    public void exportCsv(AuditRecordSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AuditRecordMapper.CSV_HEADER));
        int page = 0;
        Page<AuditRecord> chunk;
        do {
            chunk = repository.findAll(AuditRecordSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
