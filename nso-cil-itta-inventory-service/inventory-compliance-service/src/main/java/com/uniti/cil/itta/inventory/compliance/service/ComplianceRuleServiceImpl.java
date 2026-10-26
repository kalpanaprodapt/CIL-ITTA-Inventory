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
import com.uniti.cil.itta.inventory.compliance.event.ComplianceRuleEvents;
import com.uniti.cil.itta.inventory.compliance.mapper.ComplianceRuleMapper;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceRule;
import com.uniti.cil.itta.inventory.compliance.repository.ComplianceRuleRepository;
import com.uniti.cil.itta.inventory.compliance.spec.ComplianceRuleSpecifications;
import com.uniti.cil.itta.inventory.compliance.validation.ComplianceRuleValidator;
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
public class ComplianceRuleServiceImpl implements ComplianceRuleService {

    private static final Logger log = LoggerFactory.getLogger(ComplianceRuleServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ComplianceRuleRepository repository;
    private final ComplianceRuleMapper mapper;
    private final ComplianceRuleValidator validator;
    private final EventPublisher events;

    public ComplianceRuleServiceImpl(ComplianceRuleRepository repository, ComplianceRuleMapper mapper, ComplianceRuleValidator validator,
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

    private ComplianceRule load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceRule", id));
    }

    @Override
    public ComplianceRuleResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ComplianceRuleResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceRule", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ComplianceRuleResponse> search(ComplianceRuleSearchCriteria criteria, Pageable pageable) {
        Page<ComplianceRule> page = repository.findAll(ComplianceRuleSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ComplianceRuleSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ComplianceRuleResponse create(ComplianceRuleCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ComplianceRule", "code", request.getCode());
        }
        ComplianceRule entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ComplianceRule saved = repository.save(entity);
        ComplianceRuleResponse response = mapper.toResponse(saved);
        events.publish(ComplianceRuleEvents.created(response, actor()));
        log.info("Created ComplianceRule id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ComplianceRuleResponse update(UUID id, ComplianceRuleUpdateRequest request) {
        ComplianceRule entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ComplianceRule " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ComplianceRule", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ComplianceRule saved = repository.save(entity);
        ComplianceRuleResponse response = mapper.toResponse(saved);
        events.publish(ComplianceRuleEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ComplianceRuleEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ComplianceRuleResponse changeStatus(UUID id, LifecycleStatus status) {
        ComplianceRule entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ComplianceRule saved = repository.save(entity);
        ComplianceRuleResponse response = mapper.toResponse(saved);
        events.publish(ComplianceRuleEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ComplianceRule entity = load(id);
        ComplianceRuleResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ComplianceRuleEvents.deleted(snapshot, actor()));
        log.info("Deleted ComplianceRule id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ComplianceRuleResponse> bulkCreate(List<ComplianceRuleCreateRequest> requests) {
        BulkResult<ComplianceRuleResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ComplianceRule failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ComplianceRuleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ComplianceRuleResponse> result = new BulkResult<>();
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
    public void exportCsv(ComplianceRuleSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ComplianceRuleMapper.CSV_HEADER));
        int page = 0;
        Page<ComplianceRule> chunk;
        do {
            chunk = repository.findAll(ComplianceRuleSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
