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
import com.uniti.cil.itta.inventory.capacity.event.ThresholdPolicyEvents;
import com.uniti.cil.itta.inventory.capacity.mapper.ThresholdPolicyMapper;
import com.uniti.cil.itta.inventory.capacity.model.ThresholdPolicy;
import com.uniti.cil.itta.inventory.capacity.repository.ThresholdPolicyRepository;
import com.uniti.cil.itta.inventory.capacity.spec.ThresholdPolicySpecifications;
import com.uniti.cil.itta.inventory.capacity.validation.ThresholdPolicyValidator;
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
public class ThresholdPolicyServiceImpl implements ThresholdPolicyService {

    private static final Logger log = LoggerFactory.getLogger(ThresholdPolicyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ThresholdPolicyRepository repository;
    private final ThresholdPolicyMapper mapper;
    private final ThresholdPolicyValidator validator;
    private final EventPublisher events;

    public ThresholdPolicyServiceImpl(ThresholdPolicyRepository repository, ThresholdPolicyMapper mapper, ThresholdPolicyValidator validator,
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

    private ThresholdPolicy load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ThresholdPolicy", id));
    }

    @Override
    public ThresholdPolicyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ThresholdPolicyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ThresholdPolicy", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ThresholdPolicyResponse> search(ThresholdPolicySearchCriteria criteria, Pageable pageable) {
        Page<ThresholdPolicy> page = repository.findAll(ThresholdPolicySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ThresholdPolicySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ThresholdPolicyResponse create(ThresholdPolicyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ThresholdPolicy", "code", request.getCode());
        }
        ThresholdPolicy entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ThresholdPolicy saved = repository.save(entity);
        ThresholdPolicyResponse response = mapper.toResponse(saved);
        events.publish(ThresholdPolicyEvents.created(response, actor()));
        log.info("Created ThresholdPolicy id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ThresholdPolicyResponse update(UUID id, ThresholdPolicyUpdateRequest request) {
        ThresholdPolicy entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ThresholdPolicy " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ThresholdPolicy", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ThresholdPolicy saved = repository.save(entity);
        ThresholdPolicyResponse response = mapper.toResponse(saved);
        events.publish(ThresholdPolicyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ThresholdPolicyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ThresholdPolicyResponse changeStatus(UUID id, LifecycleStatus status) {
        ThresholdPolicy entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ThresholdPolicy saved = repository.save(entity);
        ThresholdPolicyResponse response = mapper.toResponse(saved);
        events.publish(ThresholdPolicyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ThresholdPolicy entity = load(id);
        ThresholdPolicyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ThresholdPolicyEvents.deleted(snapshot, actor()));
        log.info("Deleted ThresholdPolicy id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ThresholdPolicyResponse> bulkCreate(List<ThresholdPolicyCreateRequest> requests) {
        BulkResult<ThresholdPolicyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ThresholdPolicy failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ThresholdPolicyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ThresholdPolicyResponse> result = new BulkResult<>();
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
    public void exportCsv(ThresholdPolicySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ThresholdPolicyMapper.CSV_HEADER));
        int page = 0;
        Page<ThresholdPolicy> chunk;
        do {
            chunk = repository.findAll(ThresholdPolicySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
