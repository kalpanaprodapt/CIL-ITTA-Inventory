package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.event.ReconciliationResultEvents;
import com.uniti.cil.itta.inventory.discovery.mapper.ReconciliationResultMapper;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationResult;
import com.uniti.cil.itta.inventory.discovery.repository.ReconciliationResultRepository;
import com.uniti.cil.itta.inventory.discovery.spec.ReconciliationResultSpecifications;
import com.uniti.cil.itta.inventory.discovery.validation.ReconciliationResultValidator;
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
public class ReconciliationResultServiceImpl implements ReconciliationResultService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationResultServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ReconciliationResultRepository repository;
    private final ReconciliationResultMapper mapper;
    private final ReconciliationResultValidator validator;
    private final EventPublisher events;

    public ReconciliationResultServiceImpl(ReconciliationResultRepository repository, ReconciliationResultMapper mapper, ReconciliationResultValidator validator,
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

    private ReconciliationResult load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ReconciliationResult", id));
    }

    @Override
    public ReconciliationResultResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ReconciliationResultResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ReconciliationResult", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ReconciliationResultResponse> search(ReconciliationResultSearchCriteria criteria, Pageable pageable) {
        Page<ReconciliationResult> page = repository.findAll(ReconciliationResultSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ReconciliationResultSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ReconciliationResultResponse create(ReconciliationResultCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ReconciliationResult", "code", request.getCode());
        }
        ReconciliationResult entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ReconciliationResult saved = repository.save(entity);
        ReconciliationResultResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationResultEvents.created(response, actor()));
        log.info("Created ReconciliationResult id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ReconciliationResultResponse update(UUID id, ReconciliationResultUpdateRequest request) {
        ReconciliationResult entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ReconciliationResult " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ReconciliationResult", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ReconciliationResult saved = repository.save(entity);
        ReconciliationResultResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationResultEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ReconciliationResultEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ReconciliationResultResponse changeStatus(UUID id, LifecycleStatus status) {
        ReconciliationResult entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ReconciliationResult saved = repository.save(entity);
        ReconciliationResultResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationResultEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ReconciliationResult entity = load(id);
        ReconciliationResultResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ReconciliationResultEvents.deleted(snapshot, actor()));
        log.info("Deleted ReconciliationResult id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ReconciliationResultResponse> bulkCreate(List<ReconciliationResultCreateRequest> requests) {
        BulkResult<ReconciliationResultResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ReconciliationResult failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ReconciliationResultResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ReconciliationResultResponse> result = new BulkResult<>();
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
    public void exportCsv(ReconciliationResultSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ReconciliationResultMapper.CSV_HEADER));
        int page = 0;
        Page<ReconciliationResult> chunk;
        do {
            chunk = repository.findAll(ReconciliationResultSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ReconciliationResultResponse> findByJobId(UUID jobId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndJobId(tenant(), jobId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<ReconciliationResultResponse> findByRuleId(UUID ruleId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRuleId(tenant(), ruleId, pageable).map(mapper::toResponse));
    }
}
