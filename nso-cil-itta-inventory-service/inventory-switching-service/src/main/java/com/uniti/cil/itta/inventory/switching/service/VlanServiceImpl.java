package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.event.VlanEvents;
import com.uniti.cil.itta.inventory.switching.mapper.VlanMapper;
import com.uniti.cil.itta.inventory.switching.model.Vlan;
import com.uniti.cil.itta.inventory.switching.repository.VlanRepository;
import com.uniti.cil.itta.inventory.switching.spec.VlanSpecifications;
import com.uniti.cil.itta.inventory.switching.validation.VlanValidator;
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
public class VlanServiceImpl implements VlanService {

    private static final Logger log = LoggerFactory.getLogger(VlanServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VlanRepository repository;
    private final VlanMapper mapper;
    private final VlanValidator validator;
    private final EventPublisher events;

    public VlanServiceImpl(VlanRepository repository, VlanMapper mapper, VlanValidator validator,
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

    private Vlan load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Vlan", id));
    }

    @Override
    public VlanResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VlanResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Vlan", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VlanResponse> search(VlanSearchCriteria criteria, Pageable pageable) {
        Page<Vlan> page = repository.findAll(VlanSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VlanSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VlanResponse create(VlanCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Vlan", "code", request.getCode());
        }
        Vlan entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Vlan saved = repository.save(entity);
        VlanResponse response = mapper.toResponse(saved);
        events.publish(VlanEvents.created(response, actor()));
        log.info("Created Vlan id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VlanResponse update(UUID id, VlanUpdateRequest request) {
        Vlan entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Vlan " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Vlan", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Vlan saved = repository.save(entity);
        VlanResponse response = mapper.toResponse(saved);
        events.publish(VlanEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VlanEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VlanResponse changeStatus(UUID id, LifecycleStatus status) {
        Vlan entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Vlan saved = repository.save(entity);
        VlanResponse response = mapper.toResponse(saved);
        events.publish(VlanEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Vlan entity = load(id);
        VlanResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VlanEvents.deleted(snapshot, actor()));
        log.info("Deleted Vlan id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VlanResponse> bulkCreate(List<VlanCreateRequest> requests) {
        BulkResult<VlanResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Vlan failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VlanResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VlanResponse> result = new BulkResult<>();
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
    public void exportCsv(VlanSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VlanMapper.CSV_HEADER));
        int page = 0;
        Page<Vlan> chunk;
        do {
            chunk = repository.findAll(VlanSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<VlanResponse> findByPoolId(UUID poolId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPoolId(tenant(), poolId, pageable).map(mapper::toResponse));
    }
}
