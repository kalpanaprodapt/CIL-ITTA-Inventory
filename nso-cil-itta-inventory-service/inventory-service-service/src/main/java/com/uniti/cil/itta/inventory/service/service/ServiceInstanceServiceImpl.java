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
import com.uniti.cil.itta.inventory.service.event.ServiceInstanceEvents;
import com.uniti.cil.itta.inventory.service.mapper.ServiceInstanceMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceInstance;
import com.uniti.cil.itta.inventory.service.repository.ServiceInstanceRepository;
import com.uniti.cil.itta.inventory.service.spec.ServiceInstanceSpecifications;
import com.uniti.cil.itta.inventory.service.validation.ServiceInstanceValidator;
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
public class ServiceInstanceServiceImpl implements ServiceInstanceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceInstanceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ServiceInstanceRepository repository;
    private final ServiceInstanceMapper mapper;
    private final ServiceInstanceValidator validator;
    private final EventPublisher events;

    public ServiceInstanceServiceImpl(ServiceInstanceRepository repository, ServiceInstanceMapper mapper, ServiceInstanceValidator validator,
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

    private ServiceInstance load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceInstance", id));
    }

    @Override
    public ServiceInstanceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ServiceInstanceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceInstance", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ServiceInstanceResponse> search(ServiceInstanceSearchCriteria criteria, Pageable pageable) {
        Page<ServiceInstance> page = repository.findAll(ServiceInstanceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ServiceInstanceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ServiceInstanceResponse create(ServiceInstanceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ServiceInstance", "code", request.getCode());
        }
        ServiceInstance entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ServiceInstance saved = repository.save(entity);
        ServiceInstanceResponse response = mapper.toResponse(saved);
        events.publish(ServiceInstanceEvents.created(response, actor()));
        log.info("Created ServiceInstance id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ServiceInstanceResponse update(UUID id, ServiceInstanceUpdateRequest request) {
        ServiceInstance entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ServiceInstance " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ServiceInstance", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ServiceInstance saved = repository.save(entity);
        ServiceInstanceResponse response = mapper.toResponse(saved);
        events.publish(ServiceInstanceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ServiceInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ServiceInstanceResponse changeStatus(UUID id, LifecycleStatus status) {
        ServiceInstance entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ServiceInstance saved = repository.save(entity);
        ServiceInstanceResponse response = mapper.toResponse(saved);
        events.publish(ServiceInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ServiceInstance entity = load(id);
        ServiceInstanceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ServiceInstanceEvents.deleted(snapshot, actor()));
        log.info("Deleted ServiceInstance id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ServiceInstanceResponse> bulkCreate(List<ServiceInstanceCreateRequest> requests) {
        BulkResult<ServiceInstanceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ServiceInstance failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ServiceInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ServiceInstanceResponse> result = new BulkResult<>();
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
    public void exportCsv(ServiceInstanceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ServiceInstanceMapper.CSV_HEADER));
        int page = 0;
        Page<ServiceInstance> chunk;
        do {
            chunk = repository.findAll(ServiceInstanceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ServiceInstanceResponse> findByCatalogItemId(UUID catalogItemId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCatalogItemId(tenant(), catalogItemId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<ServiceInstanceResponse> findByCustomerId(UUID customerId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCustomerId(tenant(), customerId, pageable).map(mapper::toResponse));
    }
}
