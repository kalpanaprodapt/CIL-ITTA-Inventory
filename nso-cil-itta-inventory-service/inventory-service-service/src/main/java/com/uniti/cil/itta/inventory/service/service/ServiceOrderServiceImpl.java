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
import com.uniti.cil.itta.inventory.service.event.ServiceOrderEvents;
import com.uniti.cil.itta.inventory.service.mapper.ServiceOrderMapper;
import com.uniti.cil.itta.inventory.service.model.ServiceOrder;
import com.uniti.cil.itta.inventory.service.repository.ServiceOrderRepository;
import com.uniti.cil.itta.inventory.service.spec.ServiceOrderSpecifications;
import com.uniti.cil.itta.inventory.service.validation.ServiceOrderValidator;
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
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private static final Logger log = LoggerFactory.getLogger(ServiceOrderServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ServiceOrderRepository repository;
    private final ServiceOrderMapper mapper;
    private final ServiceOrderValidator validator;
    private final EventPublisher events;

    public ServiceOrderServiceImpl(ServiceOrderRepository repository, ServiceOrderMapper mapper, ServiceOrderValidator validator,
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

    private ServiceOrder load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceOrder", id));
    }

    @Override
    public ServiceOrderResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ServiceOrderResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceOrder", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ServiceOrderResponse> search(ServiceOrderSearchCriteria criteria, Pageable pageable) {
        Page<ServiceOrder> page = repository.findAll(ServiceOrderSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ServiceOrderSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ServiceOrderResponse create(ServiceOrderCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ServiceOrder", "code", request.getCode());
        }
        ServiceOrder entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ServiceOrder saved = repository.save(entity);
        ServiceOrderResponse response = mapper.toResponse(saved);
        events.publish(ServiceOrderEvents.created(response, actor()));
        log.info("Created ServiceOrder id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ServiceOrderResponse update(UUID id, ServiceOrderUpdateRequest request) {
        ServiceOrder entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ServiceOrder " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ServiceOrder", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ServiceOrder saved = repository.save(entity);
        ServiceOrderResponse response = mapper.toResponse(saved);
        events.publish(ServiceOrderEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ServiceOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ServiceOrderResponse changeStatus(UUID id, LifecycleStatus status) {
        ServiceOrder entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ServiceOrder saved = repository.save(entity);
        ServiceOrderResponse response = mapper.toResponse(saved);
        events.publish(ServiceOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ServiceOrder entity = load(id);
        ServiceOrderResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ServiceOrderEvents.deleted(snapshot, actor()));
        log.info("Deleted ServiceOrder id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ServiceOrderResponse> bulkCreate(List<ServiceOrderCreateRequest> requests) {
        BulkResult<ServiceOrderResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ServiceOrder failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ServiceOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ServiceOrderResponse> result = new BulkResult<>();
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
    public void exportCsv(ServiceOrderSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ServiceOrderMapper.CSV_HEADER));
        int page = 0;
        Page<ServiceOrder> chunk;
        do {
            chunk = repository.findAll(ServiceOrderSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ServiceOrderResponse> findByCustomerId(UUID customerId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCustomerId(tenant(), customerId, pageable).map(mapper::toResponse));
    }
}
