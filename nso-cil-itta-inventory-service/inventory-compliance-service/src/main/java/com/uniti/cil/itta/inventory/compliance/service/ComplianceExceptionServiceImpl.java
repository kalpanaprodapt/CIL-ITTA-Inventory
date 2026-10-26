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
import com.uniti.cil.itta.inventory.compliance.event.ComplianceExceptionEvents;
import com.uniti.cil.itta.inventory.compliance.mapper.ComplianceExceptionMapper;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceException;
import com.uniti.cil.itta.inventory.compliance.repository.ComplianceExceptionRepository;
import com.uniti.cil.itta.inventory.compliance.spec.ComplianceExceptionSpecifications;
import com.uniti.cil.itta.inventory.compliance.validation.ComplianceExceptionValidator;
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
public class ComplianceExceptionServiceImpl implements ComplianceExceptionService {

    private static final Logger log = LoggerFactory.getLogger(ComplianceExceptionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ComplianceExceptionRepository repository;
    private final ComplianceExceptionMapper mapper;
    private final ComplianceExceptionValidator validator;
    private final EventPublisher events;

    public ComplianceExceptionServiceImpl(ComplianceExceptionRepository repository, ComplianceExceptionMapper mapper, ComplianceExceptionValidator validator,
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

    private ComplianceException load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceException", id));
    }

    @Override
    public ComplianceExceptionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ComplianceExceptionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceException", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ComplianceExceptionResponse> search(ComplianceExceptionSearchCriteria criteria, Pageable pageable) {
        Page<ComplianceException> page = repository.findAll(ComplianceExceptionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ComplianceExceptionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ComplianceExceptionResponse create(ComplianceExceptionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ComplianceException", "code", request.getCode());
        }
        ComplianceException entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ComplianceException saved = repository.save(entity);
        ComplianceExceptionResponse response = mapper.toResponse(saved);
        events.publish(ComplianceExceptionEvents.created(response, actor()));
        log.info("Created ComplianceException id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ComplianceExceptionResponse update(UUID id, ComplianceExceptionUpdateRequest request) {
        ComplianceException entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ComplianceException " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ComplianceException", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ComplianceException saved = repository.save(entity);
        ComplianceExceptionResponse response = mapper.toResponse(saved);
        events.publish(ComplianceExceptionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ComplianceExceptionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ComplianceExceptionResponse changeStatus(UUID id, LifecycleStatus status) {
        ComplianceException entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ComplianceException saved = repository.save(entity);
        ComplianceExceptionResponse response = mapper.toResponse(saved);
        events.publish(ComplianceExceptionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ComplianceException entity = load(id);
        ComplianceExceptionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ComplianceExceptionEvents.deleted(snapshot, actor()));
        log.info("Deleted ComplianceException id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ComplianceExceptionResponse> bulkCreate(List<ComplianceExceptionCreateRequest> requests) {
        BulkResult<ComplianceExceptionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ComplianceException failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ComplianceExceptionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ComplianceExceptionResponse> result = new BulkResult<>();
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
    public void exportCsv(ComplianceExceptionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ComplianceExceptionMapper.CSV_HEADER));
        int page = 0;
        Page<ComplianceException> chunk;
        do {
            chunk = repository.findAll(ComplianceExceptionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ComplianceExceptionResponse> findByRuleId(UUID ruleId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRuleId(tenant(), ruleId, pageable).map(mapper::toResponse));
    }
}
