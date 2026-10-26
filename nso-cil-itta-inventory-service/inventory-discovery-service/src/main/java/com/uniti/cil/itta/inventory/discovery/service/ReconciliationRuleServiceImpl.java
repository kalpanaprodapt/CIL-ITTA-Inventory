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
import com.uniti.cil.itta.inventory.discovery.event.ReconciliationRuleEvents;
import com.uniti.cil.itta.inventory.discovery.mapper.ReconciliationRuleMapper;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationRule;
import com.uniti.cil.itta.inventory.discovery.repository.ReconciliationRuleRepository;
import com.uniti.cil.itta.inventory.discovery.spec.ReconciliationRuleSpecifications;
import com.uniti.cil.itta.inventory.discovery.validation.ReconciliationRuleValidator;
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
public class ReconciliationRuleServiceImpl implements ReconciliationRuleService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationRuleServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ReconciliationRuleRepository repository;
    private final ReconciliationRuleMapper mapper;
    private final ReconciliationRuleValidator validator;
    private final EventPublisher events;

    public ReconciliationRuleServiceImpl(ReconciliationRuleRepository repository, ReconciliationRuleMapper mapper, ReconciliationRuleValidator validator,
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

    private ReconciliationRule load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ReconciliationRule", id));
    }

    @Override
    public ReconciliationRuleResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ReconciliationRuleResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ReconciliationRule", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ReconciliationRuleResponse> search(ReconciliationRuleSearchCriteria criteria, Pageable pageable) {
        Page<ReconciliationRule> page = repository.findAll(ReconciliationRuleSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ReconciliationRuleSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ReconciliationRuleResponse create(ReconciliationRuleCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ReconciliationRule", "code", request.getCode());
        }
        ReconciliationRule entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ReconciliationRule saved = repository.save(entity);
        ReconciliationRuleResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationRuleEvents.created(response, actor()));
        log.info("Created ReconciliationRule id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ReconciliationRuleResponse update(UUID id, ReconciliationRuleUpdateRequest request) {
        ReconciliationRule entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ReconciliationRule " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ReconciliationRule", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ReconciliationRule saved = repository.save(entity);
        ReconciliationRuleResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationRuleEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ReconciliationRuleEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ReconciliationRuleResponse changeStatus(UUID id, LifecycleStatus status) {
        ReconciliationRule entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ReconciliationRule saved = repository.save(entity);
        ReconciliationRuleResponse response = mapper.toResponse(saved);
        events.publish(ReconciliationRuleEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ReconciliationRule entity = load(id);
        ReconciliationRuleResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ReconciliationRuleEvents.deleted(snapshot, actor()));
        log.info("Deleted ReconciliationRule id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ReconciliationRuleResponse> bulkCreate(List<ReconciliationRuleCreateRequest> requests) {
        BulkResult<ReconciliationRuleResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ReconciliationRule failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ReconciliationRuleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ReconciliationRuleResponse> result = new BulkResult<>();
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
    public void exportCsv(ReconciliationRuleSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ReconciliationRuleMapper.CSV_HEADER));
        int page = 0;
        Page<ReconciliationRule> chunk;
        do {
            chunk = repository.findAll(ReconciliationRuleSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
