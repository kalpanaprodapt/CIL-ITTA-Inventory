package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.event.LagGroupEvents;
import com.uniti.cil.itta.inventory.port.mapper.LagGroupMapper;
import com.uniti.cil.itta.inventory.port.model.LagGroup;
import com.uniti.cil.itta.inventory.port.repository.LagGroupRepository;
import com.uniti.cil.itta.inventory.port.spec.LagGroupSpecifications;
import com.uniti.cil.itta.inventory.port.validation.LagGroupValidator;
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
public class LagGroupServiceImpl implements LagGroupService {

    private static final Logger log = LoggerFactory.getLogger(LagGroupServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final LagGroupRepository repository;
    private final LagGroupMapper mapper;
    private final LagGroupValidator validator;
    private final EventPublisher events;

    public LagGroupServiceImpl(LagGroupRepository repository, LagGroupMapper mapper, LagGroupValidator validator,
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

    private LagGroup load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("LagGroup", id));
    }

    @Override
    public LagGroupResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public LagGroupResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("LagGroup", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<LagGroupResponse> search(LagGroupSearchCriteria criteria, Pageable pageable) {
        Page<LagGroup> page = repository.findAll(LagGroupSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<LagGroupSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public LagGroupResponse create(LagGroupCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("LagGroup", "code", request.getCode());
        }
        LagGroup entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        LagGroup saved = repository.save(entity);
        LagGroupResponse response = mapper.toResponse(saved);
        events.publish(LagGroupEvents.created(response, actor()));
        log.info("Created LagGroup id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public LagGroupResponse update(UUID id, LagGroupUpdateRequest request) {
        LagGroup entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "LagGroup " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("LagGroup", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        LagGroup saved = repository.save(entity);
        LagGroupResponse response = mapper.toResponse(saved);
        events.publish(LagGroupEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(LagGroupEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public LagGroupResponse changeStatus(UUID id, LifecycleStatus status) {
        LagGroup entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        LagGroup saved = repository.save(entity);
        LagGroupResponse response = mapper.toResponse(saved);
        events.publish(LagGroupEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        LagGroup entity = load(id);
        LagGroupResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(LagGroupEvents.deleted(snapshot, actor()));
        log.info("Deleted LagGroup id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<LagGroupResponse> bulkCreate(List<LagGroupCreateRequest> requests) {
        BulkResult<LagGroupResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of LagGroup failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<LagGroupResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<LagGroupResponse> result = new BulkResult<>();
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
    public void exportCsv(LagGroupSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(LagGroupMapper.CSV_HEADER));
        int page = 0;
        Page<LagGroup> chunk;
        do {
            chunk = repository.findAll(LagGroupSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<LagGroupResponse> findByDeviceId(UUID deviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDeviceId(tenant(), deviceId, pageable).map(mapper::toResponse));
    }
}
