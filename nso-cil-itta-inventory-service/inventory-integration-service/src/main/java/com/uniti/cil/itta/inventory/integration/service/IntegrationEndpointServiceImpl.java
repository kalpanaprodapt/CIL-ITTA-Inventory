package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.event.IntegrationEndpointEvents;
import com.uniti.cil.itta.inventory.integration.mapper.IntegrationEndpointMapper;
import com.uniti.cil.itta.inventory.integration.model.IntegrationEndpoint;
import com.uniti.cil.itta.inventory.integration.repository.IntegrationEndpointRepository;
import com.uniti.cil.itta.inventory.integration.spec.IntegrationEndpointSpecifications;
import com.uniti.cil.itta.inventory.integration.validation.IntegrationEndpointValidator;
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
public class IntegrationEndpointServiceImpl implements IntegrationEndpointService {

    private static final Logger log = LoggerFactory.getLogger(IntegrationEndpointServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final IntegrationEndpointRepository repository;
    private final IntegrationEndpointMapper mapper;
    private final IntegrationEndpointValidator validator;
    private final EventPublisher events;

    public IntegrationEndpointServiceImpl(IntegrationEndpointRepository repository, IntegrationEndpointMapper mapper, IntegrationEndpointValidator validator,
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

    private IntegrationEndpoint load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("IntegrationEndpoint", id));
    }

    @Override
    public IntegrationEndpointResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public IntegrationEndpointResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("IntegrationEndpoint", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<IntegrationEndpointResponse> search(IntegrationEndpointSearchCriteria criteria, Pageable pageable) {
        Page<IntegrationEndpoint> page = repository.findAll(IntegrationEndpointSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<IntegrationEndpointSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public IntegrationEndpointResponse create(IntegrationEndpointCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("IntegrationEndpoint", "code", request.getCode());
        }
        IntegrationEndpoint entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        IntegrationEndpoint saved = repository.save(entity);
        IntegrationEndpointResponse response = mapper.toResponse(saved);
        events.publish(IntegrationEndpointEvents.created(response, actor()));
        log.info("Created IntegrationEndpoint id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public IntegrationEndpointResponse update(UUID id, IntegrationEndpointUpdateRequest request) {
        IntegrationEndpoint entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "IntegrationEndpoint " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("IntegrationEndpoint", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        IntegrationEndpoint saved = repository.save(entity);
        IntegrationEndpointResponse response = mapper.toResponse(saved);
        events.publish(IntegrationEndpointEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(IntegrationEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public IntegrationEndpointResponse changeStatus(UUID id, LifecycleStatus status) {
        IntegrationEndpoint entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        IntegrationEndpoint saved = repository.save(entity);
        IntegrationEndpointResponse response = mapper.toResponse(saved);
        events.publish(IntegrationEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        IntegrationEndpoint entity = load(id);
        IntegrationEndpointResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(IntegrationEndpointEvents.deleted(snapshot, actor()));
        log.info("Deleted IntegrationEndpoint id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<IntegrationEndpointResponse> bulkCreate(List<IntegrationEndpointCreateRequest> requests) {
        BulkResult<IntegrationEndpointResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of IntegrationEndpoint failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<IntegrationEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<IntegrationEndpointResponse> result = new BulkResult<>();
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
    public void exportCsv(IntegrationEndpointSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(IntegrationEndpointMapper.CSV_HEADER));
        int page = 0;
        Page<IntegrationEndpoint> chunk;
        do {
            chunk = repository.findAll(IntegrationEndpointSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
