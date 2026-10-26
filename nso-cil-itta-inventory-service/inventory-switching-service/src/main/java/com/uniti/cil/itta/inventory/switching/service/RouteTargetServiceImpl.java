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
import com.uniti.cil.itta.inventory.switching.event.RouteTargetEvents;
import com.uniti.cil.itta.inventory.switching.mapper.RouteTargetMapper;
import com.uniti.cil.itta.inventory.switching.model.RouteTarget;
import com.uniti.cil.itta.inventory.switching.repository.RouteTargetRepository;
import com.uniti.cil.itta.inventory.switching.spec.RouteTargetSpecifications;
import com.uniti.cil.itta.inventory.switching.validation.RouteTargetValidator;
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
public class RouteTargetServiceImpl implements RouteTargetService {

    private static final Logger log = LoggerFactory.getLogger(RouteTargetServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RouteTargetRepository repository;
    private final RouteTargetMapper mapper;
    private final RouteTargetValidator validator;
    private final EventPublisher events;

    public RouteTargetServiceImpl(RouteTargetRepository repository, RouteTargetMapper mapper, RouteTargetValidator validator,
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

    private RouteTarget load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteTarget", id));
    }

    @Override
    public RouteTargetResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RouteTargetResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("RouteTarget", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RouteTargetResponse> search(RouteTargetSearchCriteria criteria, Pageable pageable) {
        Page<RouteTarget> page = repository.findAll(RouteTargetSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RouteTargetSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RouteTargetResponse create(RouteTargetCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("RouteTarget", "code", request.getCode());
        }
        RouteTarget entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        RouteTarget saved = repository.save(entity);
        RouteTargetResponse response = mapper.toResponse(saved);
        events.publish(RouteTargetEvents.created(response, actor()));
        log.info("Created RouteTarget id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RouteTargetResponse update(UUID id, RouteTargetUpdateRequest request) {
        RouteTarget entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "RouteTarget " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("RouteTarget", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        RouteTarget saved = repository.save(entity);
        RouteTargetResponse response = mapper.toResponse(saved);
        events.publish(RouteTargetEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RouteTargetEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RouteTargetResponse changeStatus(UUID id, LifecycleStatus status) {
        RouteTarget entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        RouteTarget saved = repository.save(entity);
        RouteTargetResponse response = mapper.toResponse(saved);
        events.publish(RouteTargetEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        RouteTarget entity = load(id);
        RouteTargetResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RouteTargetEvents.deleted(snapshot, actor()));
        log.info("Deleted RouteTarget id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RouteTargetResponse> bulkCreate(List<RouteTargetCreateRequest> requests) {
        BulkResult<RouteTargetResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of RouteTarget failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RouteTargetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RouteTargetResponse> result = new BulkResult<>();
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
    public void exportCsv(RouteTargetSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RouteTargetMapper.CSV_HEADER));
        int page = 0;
        Page<RouteTarget> chunk;
        do {
            chunk = repository.findAll(RouteTargetSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RouteTargetResponse> findByVrfId(UUID vrfId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVrfId(tenant(), vrfId, pageable).map(mapper::toResponse));
    }
}
