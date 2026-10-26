package com.uniti.cil.itta.inventory.facility.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.event.RackUnitEvents;
import com.uniti.cil.itta.inventory.facility.mapper.RackUnitMapper;
import com.uniti.cil.itta.inventory.facility.model.RackUnit;
import com.uniti.cil.itta.inventory.facility.repository.RackUnitRepository;
import com.uniti.cil.itta.inventory.facility.spec.RackUnitSpecifications;
import com.uniti.cil.itta.inventory.facility.validation.RackUnitValidator;
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
public class RackUnitServiceImpl implements RackUnitService {

    private static final Logger log = LoggerFactory.getLogger(RackUnitServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RackUnitRepository repository;
    private final RackUnitMapper mapper;
    private final RackUnitValidator validator;
    private final EventPublisher events;

    public RackUnitServiceImpl(RackUnitRepository repository, RackUnitMapper mapper, RackUnitValidator validator,
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

    private RackUnit load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("RackUnit", id));
    }

    @Override
    public RackUnitResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RackUnitResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("RackUnit", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RackUnitResponse> search(RackUnitSearchCriteria criteria, Pageable pageable) {
        Page<RackUnit> page = repository.findAll(RackUnitSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RackUnitSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RackUnitResponse create(RackUnitCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("RackUnit", "code", request.getCode());
        }
        RackUnit entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        RackUnit saved = repository.save(entity);
        RackUnitResponse response = mapper.toResponse(saved);
        events.publish(RackUnitEvents.created(response, actor()));
        log.info("Created RackUnit id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RackUnitResponse update(UUID id, RackUnitUpdateRequest request) {
        RackUnit entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "RackUnit " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("RackUnit", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        RackUnit saved = repository.save(entity);
        RackUnitResponse response = mapper.toResponse(saved);
        events.publish(RackUnitEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RackUnitEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RackUnitResponse changeStatus(UUID id, LifecycleStatus status) {
        RackUnit entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        RackUnit saved = repository.save(entity);
        RackUnitResponse response = mapper.toResponse(saved);
        events.publish(RackUnitEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        RackUnit entity = load(id);
        RackUnitResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RackUnitEvents.deleted(snapshot, actor()));
        log.info("Deleted RackUnit id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RackUnitResponse> bulkCreate(List<RackUnitCreateRequest> requests) {
        BulkResult<RackUnitResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of RackUnit failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RackUnitResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RackUnitResponse> result = new BulkResult<>();
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
    public void exportCsv(RackUnitSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RackUnitMapper.CSV_HEADER));
        int page = 0;
        Page<RackUnit> chunk;
        do {
            chunk = repository.findAll(RackUnitSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RackUnitResponse> findByRackId(UUID rackId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRackId(tenant(), rackId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<RackUnitResponse> findByMountedDeviceId(UUID mountedDeviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndMountedDeviceId(tenant(), mountedDeviceId, pageable).map(mapper::toResponse));
    }
}
