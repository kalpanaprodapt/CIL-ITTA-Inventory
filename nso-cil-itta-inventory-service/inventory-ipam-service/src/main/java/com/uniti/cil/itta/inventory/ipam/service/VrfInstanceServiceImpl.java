package com.uniti.cil.itta.inventory.ipam.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.event.VrfInstanceEvents;
import com.uniti.cil.itta.inventory.ipam.mapper.VrfInstanceMapper;
import com.uniti.cil.itta.inventory.ipam.model.VrfInstance;
import com.uniti.cil.itta.inventory.ipam.repository.VrfInstanceRepository;
import com.uniti.cil.itta.inventory.ipam.spec.VrfInstanceSpecifications;
import com.uniti.cil.itta.inventory.ipam.validation.VrfInstanceValidator;
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
public class VrfInstanceServiceImpl implements VrfInstanceService {

    private static final Logger log = LoggerFactory.getLogger(VrfInstanceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VrfInstanceRepository repository;
    private final VrfInstanceMapper mapper;
    private final VrfInstanceValidator validator;
    private final EventPublisher events;

    public VrfInstanceServiceImpl(VrfInstanceRepository repository, VrfInstanceMapper mapper, VrfInstanceValidator validator,
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

    private VrfInstance load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("VrfInstance", id));
    }

    @Override
    public VrfInstanceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VrfInstanceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("VrfInstance", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VrfInstanceResponse> search(VrfInstanceSearchCriteria criteria, Pageable pageable) {
        Page<VrfInstance> page = repository.findAll(VrfInstanceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VrfInstanceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VrfInstanceResponse create(VrfInstanceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("VrfInstance", "code", request.getCode());
        }
        VrfInstance entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        VrfInstance saved = repository.save(entity);
        VrfInstanceResponse response = mapper.toResponse(saved);
        events.publish(VrfInstanceEvents.created(response, actor()));
        log.info("Created VrfInstance id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VrfInstanceResponse update(UUID id, VrfInstanceUpdateRequest request) {
        VrfInstance entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "VrfInstance " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("VrfInstance", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        VrfInstance saved = repository.save(entity);
        VrfInstanceResponse response = mapper.toResponse(saved);
        events.publish(VrfInstanceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VrfInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VrfInstanceResponse changeStatus(UUID id, LifecycleStatus status) {
        VrfInstance entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        VrfInstance saved = repository.save(entity);
        VrfInstanceResponse response = mapper.toResponse(saved);
        events.publish(VrfInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        VrfInstance entity = load(id);
        VrfInstanceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VrfInstanceEvents.deleted(snapshot, actor()));
        log.info("Deleted VrfInstance id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VrfInstanceResponse> bulkCreate(List<VrfInstanceCreateRequest> requests) {
        BulkResult<VrfInstanceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of VrfInstance failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VrfInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VrfInstanceResponse> result = new BulkResult<>();
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
    public void exportCsv(VrfInstanceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VrfInstanceMapper.CSV_HEADER));
        int page = 0;
        Page<VrfInstance> chunk;
        do {
            chunk = repository.findAll(VrfInstanceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<VrfInstanceResponse> findByDeviceId(UUID deviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDeviceId(tenant(), deviceId, pageable).map(mapper::toResponse));
    }
}
