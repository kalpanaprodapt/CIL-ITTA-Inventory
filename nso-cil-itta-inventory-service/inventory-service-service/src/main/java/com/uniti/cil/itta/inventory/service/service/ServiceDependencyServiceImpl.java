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
import com.uniti.cil.itta.inventory.service.event.ServiceDependencyEvents;
import com.uniti.cil.itta.inventory.service.mapper.ServiceDependencyMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceDependency;
import com.uniti.cil.itta.inventory.service.repository.ServiceDependencyRepository;
import com.uniti.cil.itta.inventory.service.spec.ServiceDependencySpecifications;
import com.uniti.cil.itta.inventory.service.validation.ServiceDependencyValidator;
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
public class ServiceDependencyServiceImpl implements ServiceDependencyService {

    private static final Logger log = LoggerFactory.getLogger(ServiceDependencyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ServiceDependencyRepository repository;
    private final ServiceDependencyMapper mapper;
    private final ServiceDependencyValidator validator;
    private final EventPublisher events;

    public ServiceDependencyServiceImpl(ServiceDependencyRepository repository, ServiceDependencyMapper mapper, ServiceDependencyValidator validator,
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

    private ServiceDependency load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceDependency", id));
    }

    @Override
    public ServiceDependencyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ServiceDependencyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceDependency", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ServiceDependencyResponse> search(ServiceDependencySearchCriteria criteria, Pageable pageable) {
        Page<ServiceDependency> page = repository.findAll(ServiceDependencySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ServiceDependencySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ServiceDependencyResponse create(ServiceDependencyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ServiceDependency", "code", request.getCode());
        }
        ServiceDependency entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ServiceDependency saved = repository.save(entity);
        ServiceDependencyResponse response = mapper.toResponse(saved);
        events.publish(ServiceDependencyEvents.created(response, actor()));
        log.info("Created ServiceDependency id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ServiceDependencyResponse update(UUID id, ServiceDependencyUpdateRequest request) {
        ServiceDependency entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ServiceDependency " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ServiceDependency", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ServiceDependency saved = repository.save(entity);
        ServiceDependencyResponse response = mapper.toResponse(saved);
        events.publish(ServiceDependencyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ServiceDependencyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ServiceDependencyResponse changeStatus(UUID id, LifecycleStatus status) {
        ServiceDependency entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ServiceDependency saved = repository.save(entity);
        ServiceDependencyResponse response = mapper.toResponse(saved);
        events.publish(ServiceDependencyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ServiceDependency entity = load(id);
        ServiceDependencyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ServiceDependencyEvents.deleted(snapshot, actor()));
        log.info("Deleted ServiceDependency id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ServiceDependencyResponse> bulkCreate(List<ServiceDependencyCreateRequest> requests) {
        BulkResult<ServiceDependencyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ServiceDependency failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ServiceDependencyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ServiceDependencyResponse> result = new BulkResult<>();
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
    public void exportCsv(ServiceDependencySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ServiceDependencyMapper.CSV_HEADER));
        int page = 0;
        Page<ServiceDependency> chunk;
        do {
            chunk = repository.findAll(ServiceDependencySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ServiceDependencyResponse> findByServiceId(UUID serviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndServiceId(tenant(), serviceId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<ServiceDependencyResponse> findByDependsOnId(UUID dependsOnId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDependsOnId(tenant(), dependsOnId, pageable).map(mapper::toResponse));
    }
}
