package com.uniti.cil.itta.inventory.service.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.event.ServiceEndpointEvents;
import com.uniti.cil.itta.inventory.service.mapper.ServiceEndpointMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceEndpoint;
import com.uniti.cil.itta.inventory.service.repository.ServiceEndpointRepository;
import com.uniti.cil.itta.inventory.service.spec.ServiceEndpointSpecifications;
import com.uniti.cil.itta.inventory.service.validation.ServiceEndpointValidator;
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
public class ServiceEndpointServiceImpl implements ServiceEndpointService {

    private static final Logger log = LoggerFactory.getLogger(ServiceEndpointServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ServiceEndpointRepository repository;
    private final ServiceEndpointMapper mapper;
    private final ServiceEndpointValidator validator;
    private final EventPublisher events;

    public ServiceEndpointServiceImpl(ServiceEndpointRepository repository, ServiceEndpointMapper mapper, ServiceEndpointValidator validator,
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

    private ServiceEndpoint load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceEndpoint", id));
    }

    @Override
    public ServiceEndpointResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ServiceEndpointResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceEndpoint", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ServiceEndpointResponse> search(ServiceEndpointSearchCriteria criteria, Pageable pageable) {
        Page<ServiceEndpoint> page = repository.findAll(ServiceEndpointSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ServiceEndpointSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ServiceEndpointResponse create(ServiceEndpointCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ServiceEndpoint", "code", request.getCode());
        }
        ServiceEndpoint entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ServiceEndpoint saved = repository.save(entity);
        ServiceEndpointResponse response = mapper.toResponse(saved);
        events.publish(ServiceEndpointEvents.created(response, actor()));
        log.info("Created ServiceEndpoint id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ServiceEndpointResponse update(UUID id, ServiceEndpointUpdateRequest request) {
        ServiceEndpoint entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ServiceEndpoint " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ServiceEndpoint", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ServiceEndpoint saved = repository.save(entity);
        ServiceEndpointResponse response = mapper.toResponse(saved);
        events.publish(ServiceEndpointEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ServiceEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ServiceEndpointResponse changeStatus(UUID id, LifecycleStatus status) {
        ServiceEndpoint entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ServiceEndpoint saved = repository.save(entity);
        ServiceEndpointResponse response = mapper.toResponse(saved);
        events.publish(ServiceEndpointEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ServiceEndpoint entity = load(id);
        ServiceEndpointResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ServiceEndpointEvents.deleted(snapshot, actor()));
        log.info("Deleted ServiceEndpoint id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ServiceEndpointResponse> bulkCreate(List<ServiceEndpointCreateRequest> requests) {
        BulkResult<ServiceEndpointResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ServiceEndpoint failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ServiceEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ServiceEndpointResponse> result = new BulkResult<>();
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
    public void exportCsv(ServiceEndpointSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ServiceEndpointMapper.CSV_HEADER));
        int page = 0;
        Page<ServiceEndpoint> chunk;
        do {
            chunk = repository.findAll(ServiceEndpointSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ServiceEndpointResponse> findByServiceId(UUID serviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndServiceId(tenant(), serviceId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<ServiceEndpointResponse> findBySiteId(UUID siteId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSiteId(tenant(), siteId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<ServiceEndpointResponse> findByPortId(UUID portId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPortId(tenant(), portId, pageable).map(mapper::toResponse));
    }
}
