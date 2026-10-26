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
import com.uniti.cil.itta.inventory.switching.event.VlanPoolEvents;
import com.uniti.cil.itta.inventory.switching.mapper.VlanPoolMapper;
import com.uniti.cil.itta.inventory.switching.model.VlanPool;
import com.uniti.cil.itta.inventory.switching.repository.VlanPoolRepository;
import com.uniti.cil.itta.inventory.switching.spec.VlanPoolSpecifications;
import com.uniti.cil.itta.inventory.switching.validation.VlanPoolValidator;
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
public class VlanPoolServiceImpl implements VlanPoolService {

    private static final Logger log = LoggerFactory.getLogger(VlanPoolServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VlanPoolRepository repository;
    private final VlanPoolMapper mapper;
    private final VlanPoolValidator validator;
    private final EventPublisher events;

    public VlanPoolServiceImpl(VlanPoolRepository repository, VlanPoolMapper mapper, VlanPoolValidator validator,
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

    private VlanPool load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("VlanPool", id));
    }

    @Override
    public VlanPoolResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VlanPoolResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("VlanPool", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VlanPoolResponse> search(VlanPoolSearchCriteria criteria, Pageable pageable) {
        Page<VlanPool> page = repository.findAll(VlanPoolSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VlanPoolSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VlanPoolResponse create(VlanPoolCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("VlanPool", "code", request.getCode());
        }
        VlanPool entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        VlanPool saved = repository.save(entity);
        VlanPoolResponse response = mapper.toResponse(saved);
        events.publish(VlanPoolEvents.created(response, actor()));
        log.info("Created VlanPool id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VlanPoolResponse update(UUID id, VlanPoolUpdateRequest request) {
        VlanPool entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "VlanPool " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("VlanPool", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        VlanPool saved = repository.save(entity);
        VlanPoolResponse response = mapper.toResponse(saved);
        events.publish(VlanPoolEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VlanPoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VlanPoolResponse changeStatus(UUID id, LifecycleStatus status) {
        VlanPool entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        VlanPool saved = repository.save(entity);
        VlanPoolResponse response = mapper.toResponse(saved);
        events.publish(VlanPoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        VlanPool entity = load(id);
        VlanPoolResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VlanPoolEvents.deleted(snapshot, actor()));
        log.info("Deleted VlanPool id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VlanPoolResponse> bulkCreate(List<VlanPoolCreateRequest> requests) {
        BulkResult<VlanPoolResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of VlanPool failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VlanPoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VlanPoolResponse> result = new BulkResult<>();
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
    public void exportCsv(VlanPoolSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VlanPoolMapper.CSV_HEADER));
        int page = 0;
        Page<VlanPool> chunk;
        do {
            chunk = repository.findAll(VlanPoolSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
