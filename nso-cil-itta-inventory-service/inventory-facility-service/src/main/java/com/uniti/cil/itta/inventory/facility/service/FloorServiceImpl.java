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
import com.uniti.cil.itta.inventory.facility.event.FloorEvents;
import com.uniti.cil.itta.inventory.facility.mapper.FloorMapper;
import com.uniti.cil.itta.inventory.facility.model.Floor;
import com.uniti.cil.itta.inventory.facility.repository.FloorRepository;
import com.uniti.cil.itta.inventory.facility.spec.FloorSpecifications;
import com.uniti.cil.itta.inventory.facility.validation.FloorValidator;
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
public class FloorServiceImpl implements FloorService {

    private static final Logger log = LoggerFactory.getLogger(FloorServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final FloorRepository repository;
    private final FloorMapper mapper;
    private final FloorValidator validator;
    private final EventPublisher events;

    public FloorServiceImpl(FloorRepository repository, FloorMapper mapper, FloorValidator validator,
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

    private Floor load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor", id));
    }

    @Override
    public FloorResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public FloorResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Floor", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<FloorResponse> search(FloorSearchCriteria criteria, Pageable pageable) {
        Page<Floor> page = repository.findAll(FloorSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<FloorSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public FloorResponse create(FloorCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Floor", "code", request.getCode());
        }
        Floor entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Floor saved = repository.save(entity);
        FloorResponse response = mapper.toResponse(saved);
        events.publish(FloorEvents.created(response, actor()));
        log.info("Created Floor id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public FloorResponse update(UUID id, FloorUpdateRequest request) {
        Floor entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Floor " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Floor", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Floor saved = repository.save(entity);
        FloorResponse response = mapper.toResponse(saved);
        events.publish(FloorEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(FloorEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public FloorResponse changeStatus(UUID id, LifecycleStatus status) {
        Floor entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Floor saved = repository.save(entity);
        FloorResponse response = mapper.toResponse(saved);
        events.publish(FloorEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Floor entity = load(id);
        FloorResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(FloorEvents.deleted(snapshot, actor()));
        log.info("Deleted Floor id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<FloorResponse> bulkCreate(List<FloorCreateRequest> requests) {
        BulkResult<FloorResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Floor failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<FloorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<FloorResponse> result = new BulkResult<>();
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
    public void exportCsv(FloorSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(FloorMapper.CSV_HEADER));
        int page = 0;
        Page<Floor> chunk;
        do {
            chunk = repository.findAll(FloorSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<FloorResponse> findByBuildingId(UUID buildingId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndBuildingId(tenant(), buildingId, pageable).map(mapper::toResponse));
    }
}
