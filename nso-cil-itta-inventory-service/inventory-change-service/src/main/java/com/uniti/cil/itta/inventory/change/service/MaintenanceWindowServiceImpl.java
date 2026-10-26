package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.event.MaintenanceWindowEvents;
import com.uniti.cil.itta.inventory.change.mapper.MaintenanceWindowMapper;
import com.uniti.cil.itta.inventory.change.model.MaintenanceWindow;
import com.uniti.cil.itta.inventory.change.repository.MaintenanceWindowRepository;
import com.uniti.cil.itta.inventory.change.spec.MaintenanceWindowSpecifications;
import com.uniti.cil.itta.inventory.change.validation.MaintenanceWindowValidator;
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
public class MaintenanceWindowServiceImpl implements MaintenanceWindowService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceWindowServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final MaintenanceWindowRepository repository;
    private final MaintenanceWindowMapper mapper;
    private final MaintenanceWindowValidator validator;
    private final EventPublisher events;

    public MaintenanceWindowServiceImpl(MaintenanceWindowRepository repository, MaintenanceWindowMapper mapper, MaintenanceWindowValidator validator,
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

    private MaintenanceWindow load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceWindow", id));
    }

    @Override
    public MaintenanceWindowResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public MaintenanceWindowResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceWindow", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<MaintenanceWindowResponse> search(MaintenanceWindowSearchCriteria criteria, Pageable pageable) {
        Page<MaintenanceWindow> page = repository.findAll(MaintenanceWindowSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<MaintenanceWindowSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public MaintenanceWindowResponse create(MaintenanceWindowCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("MaintenanceWindow", "code", request.getCode());
        }
        MaintenanceWindow entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        MaintenanceWindow saved = repository.save(entity);
        MaintenanceWindowResponse response = mapper.toResponse(saved);
        events.publish(MaintenanceWindowEvents.created(response, actor()));
        log.info("Created MaintenanceWindow id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public MaintenanceWindowResponse update(UUID id, MaintenanceWindowUpdateRequest request) {
        MaintenanceWindow entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "MaintenanceWindow " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("MaintenanceWindow", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        MaintenanceWindow saved = repository.save(entity);
        MaintenanceWindowResponse response = mapper.toResponse(saved);
        events.publish(MaintenanceWindowEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(MaintenanceWindowEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public MaintenanceWindowResponse changeStatus(UUID id, LifecycleStatus status) {
        MaintenanceWindow entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        MaintenanceWindow saved = repository.save(entity);
        MaintenanceWindowResponse response = mapper.toResponse(saved);
        events.publish(MaintenanceWindowEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        MaintenanceWindow entity = load(id);
        MaintenanceWindowResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(MaintenanceWindowEvents.deleted(snapshot, actor()));
        log.info("Deleted MaintenanceWindow id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<MaintenanceWindowResponse> bulkCreate(List<MaintenanceWindowCreateRequest> requests) {
        BulkResult<MaintenanceWindowResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of MaintenanceWindow failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<MaintenanceWindowResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<MaintenanceWindowResponse> result = new BulkResult<>();
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
    public void exportCsv(MaintenanceWindowSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(MaintenanceWindowMapper.CSV_HEADER));
        int page = 0;
        Page<MaintenanceWindow> chunk;
        do {
            chunk = repository.findAll(MaintenanceWindowSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
