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
import com.uniti.cil.itta.inventory.facility.event.RoomEvents;
import com.uniti.cil.itta.inventory.facility.mapper.RoomMapper;
import com.uniti.cil.itta.inventory.facility.model.Room;
import com.uniti.cil.itta.inventory.facility.repository.RoomRepository;
import com.uniti.cil.itta.inventory.facility.spec.RoomSpecifications;
import com.uniti.cil.itta.inventory.facility.validation.RoomValidator;
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
public class RoomServiceImpl implements RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RoomRepository repository;
    private final RoomMapper mapper;
    private final RoomValidator validator;
    private final EventPublisher events;

    public RoomServiceImpl(RoomRepository repository, RoomMapper mapper, RoomValidator validator,
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

    private Room load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", id));
    }

    @Override
    public RoomResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RoomResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Room", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RoomResponse> search(RoomSearchCriteria criteria, Pageable pageable) {
        Page<Room> page = repository.findAll(RoomSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RoomSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RoomResponse create(RoomCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Room", "code", request.getCode());
        }
        Room entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Room saved = repository.save(entity);
        RoomResponse response = mapper.toResponse(saved);
        events.publish(RoomEvents.created(response, actor()));
        log.info("Created Room id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RoomResponse update(UUID id, RoomUpdateRequest request) {
        Room entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Room " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Room", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Room saved = repository.save(entity);
        RoomResponse response = mapper.toResponse(saved);
        events.publish(RoomEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RoomEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RoomResponse changeStatus(UUID id, LifecycleStatus status) {
        Room entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Room saved = repository.save(entity);
        RoomResponse response = mapper.toResponse(saved);
        events.publish(RoomEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Room entity = load(id);
        RoomResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RoomEvents.deleted(snapshot, actor()));
        log.info("Deleted Room id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RoomResponse> bulkCreate(List<RoomCreateRequest> requests) {
        BulkResult<RoomResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Room failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RoomResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RoomResponse> result = new BulkResult<>();
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
    public void exportCsv(RoomSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RoomMapper.CSV_HEADER));
        int page = 0;
        Page<Room> chunk;
        do {
            chunk = repository.findAll(RoomSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RoomResponse> findByFloorId(UUID floorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndFloorId(tenant(), floorId, pageable).map(mapper::toResponse));
    }
}
