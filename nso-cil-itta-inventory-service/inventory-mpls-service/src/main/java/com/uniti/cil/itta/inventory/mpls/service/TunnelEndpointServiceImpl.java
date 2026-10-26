package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.event.TunnelEndpointEvents;
import com.uniti.cil.itta.inventory.mpls.mapper.TunnelEndpointMapper;
import com.uniti.cil.itta.inventory.mpls.model.TunnelEndpoint;
import com.uniti.cil.itta.inventory.mpls.repository.TunnelEndpointRepository;
import com.uniti.cil.itta.inventory.mpls.spec.TunnelEndpointSpecifications;
import com.uniti.cil.itta.inventory.mpls.validation.TunnelEndpointValidator;
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
public class TunnelEndpointServiceImpl implements TunnelEndpointService {

    private static final Logger log = LoggerFactory.getLogger(TunnelEndpointServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final TunnelEndpointRepository repository;
    private final TunnelEndpointMapper mapper;
    private final TunnelEndpointValidator validator;
    private final EventPublisher events;

    public TunnelEndpointServiceImpl(TunnelEndpointRepository repository, TunnelEndpointMapper mapper, TunnelEndpointValidator validator,
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

    private TunnelEndpoint load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("TunnelEndpoint", id));
    }

    @Override
    public TunnelEndpointResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public TunnelEndpointResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("TunnelEndpoint", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<TunnelEndpointResponse> search(TunnelEndpointSearchCriteria criteria, Pageable pageable) {
        Page<TunnelEndpoint> page = repository.findAll(TunnelEndpointSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<TunnelEndpointSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public TunnelEndpointResponse create(TunnelEndpointCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("TunnelEndpoint", "code", request.getCode());
        }
        TunnelEndpoint entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        TunnelEndpoint saved = repository.save(entity);
        TunnelEndpointResponse response = mapper.toResponse(saved);
        events.publish(TunnelEndpointEvents.created(response, actor()));
        log.info("Created TunnelEndpoint id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public TunnelEndpointResponse update(UUID id, TunnelEndpointUpdateRequest request) {
        TunnelEndpoint entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "TunnelEndpoint " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("TunnelEndpoint", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        TunnelEndpoint saved = repository.save(entity);
        TunnelEndpointResponse response = mapper.toResponse(saved);
        events.publish(TunnelEndpointEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(TunnelEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public TunnelEndpointResponse changeStatus(UUID id, LifecycleStatus status) {
        TunnelEndpoint entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        TunnelEndpoint saved = repository.save(entity);
        TunnelEndpointResponse response = mapper.toResponse(saved);
        events.publish(TunnelEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TunnelEndpoint entity = load(id);
        TunnelEndpointResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(TunnelEndpointEvents.deleted(snapshot, actor()));
        log.info("Deleted TunnelEndpoint id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<TunnelEndpointResponse> bulkCreate(List<TunnelEndpointCreateRequest> requests) {
        BulkResult<TunnelEndpointResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of TunnelEndpoint failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<TunnelEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<TunnelEndpointResponse> result = new BulkResult<>();
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
    public void exportCsv(TunnelEndpointSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(TunnelEndpointMapper.CSV_HEADER));
        int page = 0;
        Page<TunnelEndpoint> chunk;
        do {
            chunk = repository.findAll(TunnelEndpointSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<TunnelEndpointResponse> findByDeviceId(UUID deviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDeviceId(tenant(), deviceId, pageable).map(mapper::toResponse));
    }
}
