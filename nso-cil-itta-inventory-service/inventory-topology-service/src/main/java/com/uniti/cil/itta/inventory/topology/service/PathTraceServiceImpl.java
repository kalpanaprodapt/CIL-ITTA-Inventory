package com.uniti.cil.itta.inventory.topology.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.event.PathTraceEvents;
import com.uniti.cil.itta.inventory.topology.mapper.PathTraceMapper;
import com.uniti.cil.itta.inventory.topology.model.PathTrace;
import com.uniti.cil.itta.inventory.topology.repository.PathTraceRepository;
import com.uniti.cil.itta.inventory.topology.spec.PathTraceSpecifications;
import com.uniti.cil.itta.inventory.topology.validation.PathTraceValidator;
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
public class PathTraceServiceImpl implements PathTraceService {

    private static final Logger log = LoggerFactory.getLogger(PathTraceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PathTraceRepository repository;
    private final PathTraceMapper mapper;
    private final PathTraceValidator validator;
    private final EventPublisher events;

    public PathTraceServiceImpl(PathTraceRepository repository, PathTraceMapper mapper, PathTraceValidator validator,
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

    private PathTrace load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PathTrace", id));
    }

    @Override
    public PathTraceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PathTraceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PathTrace", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PathTraceResponse> search(PathTraceSearchCriteria criteria, Pageable pageable) {
        Page<PathTrace> page = repository.findAll(PathTraceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PathTraceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PathTraceResponse create(PathTraceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PathTrace", "code", request.getCode());
        }
        PathTrace entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PathTrace saved = repository.save(entity);
        PathTraceResponse response = mapper.toResponse(saved);
        events.publish(PathTraceEvents.created(response, actor()));
        log.info("Created PathTrace id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PathTraceResponse update(UUID id, PathTraceUpdateRequest request) {
        PathTrace entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PathTrace " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PathTrace", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PathTrace saved = repository.save(entity);
        PathTraceResponse response = mapper.toResponse(saved);
        events.publish(PathTraceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PathTraceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PathTraceResponse changeStatus(UUID id, LifecycleStatus status) {
        PathTrace entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PathTrace saved = repository.save(entity);
        PathTraceResponse response = mapper.toResponse(saved);
        events.publish(PathTraceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PathTrace entity = load(id);
        PathTraceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PathTraceEvents.deleted(snapshot, actor()));
        log.info("Deleted PathTrace id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PathTraceResponse> bulkCreate(List<PathTraceCreateRequest> requests) {
        BulkResult<PathTraceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PathTrace failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PathTraceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PathTraceResponse> result = new BulkResult<>();
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
    public void exportCsv(PathTraceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PathTraceMapper.CSV_HEADER));
        int page = 0;
        Page<PathTrace> chunk;
        do {
            chunk = repository.findAll(PathTraceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
