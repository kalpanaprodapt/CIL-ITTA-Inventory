package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.event.CapacityPlanEvents;
import com.uniti.cil.itta.inventory.capacity.mapper.CapacityPlanMapper;
import com.uniti.cil.itta.inventory.capacity.model.CapacityPlan;
import com.uniti.cil.itta.inventory.capacity.repository.CapacityPlanRepository;
import com.uniti.cil.itta.inventory.capacity.spec.CapacityPlanSpecifications;
import com.uniti.cil.itta.inventory.capacity.validation.CapacityPlanValidator;
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
public class CapacityPlanServiceImpl implements CapacityPlanService {

    private static final Logger log = LoggerFactory.getLogger(CapacityPlanServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CapacityPlanRepository repository;
    private final CapacityPlanMapper mapper;
    private final CapacityPlanValidator validator;
    private final EventPublisher events;

    public CapacityPlanServiceImpl(CapacityPlanRepository repository, CapacityPlanMapper mapper, CapacityPlanValidator validator,
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

    private CapacityPlan load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("CapacityPlan", id));
    }

    @Override
    public CapacityPlanResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CapacityPlanResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("CapacityPlan", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CapacityPlanResponse> search(CapacityPlanSearchCriteria criteria, Pageable pageable) {
        Page<CapacityPlan> page = repository.findAll(CapacityPlanSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CapacityPlanSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CapacityPlanResponse create(CapacityPlanCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("CapacityPlan", "code", request.getCode());
        }
        CapacityPlan entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        CapacityPlan saved = repository.save(entity);
        CapacityPlanResponse response = mapper.toResponse(saved);
        events.publish(CapacityPlanEvents.created(response, actor()));
        log.info("Created CapacityPlan id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CapacityPlanResponse update(UUID id, CapacityPlanUpdateRequest request) {
        CapacityPlan entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "CapacityPlan " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("CapacityPlan", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        CapacityPlan saved = repository.save(entity);
        CapacityPlanResponse response = mapper.toResponse(saved);
        events.publish(CapacityPlanEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CapacityPlanEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CapacityPlanResponse changeStatus(UUID id, LifecycleStatus status) {
        CapacityPlan entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        CapacityPlan saved = repository.save(entity);
        CapacityPlanResponse response = mapper.toResponse(saved);
        events.publish(CapacityPlanEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CapacityPlan entity = load(id);
        CapacityPlanResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CapacityPlanEvents.deleted(snapshot, actor()));
        log.info("Deleted CapacityPlan id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CapacityPlanResponse> bulkCreate(List<CapacityPlanCreateRequest> requests) {
        BulkResult<CapacityPlanResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of CapacityPlan failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CapacityPlanResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CapacityPlanResponse> result = new BulkResult<>();
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
    public void exportCsv(CapacityPlanSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CapacityPlanMapper.CSV_HEADER));
        int page = 0;
        Page<CapacityPlan> chunk;
        do {
            chunk = repository.findAll(CapacityPlanSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
