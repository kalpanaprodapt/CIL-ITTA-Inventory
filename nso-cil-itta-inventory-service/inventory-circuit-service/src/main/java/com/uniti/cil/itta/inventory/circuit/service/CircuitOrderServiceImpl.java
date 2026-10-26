package com.uniti.cil.itta.inventory.circuit.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.event.CircuitOrderEvents;
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitOrderMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitOrder;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitOrderRepository;
import com.uniti.cil.itta.inventory.circuit.spec.CircuitOrderSpecifications;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitOrderValidator;
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
public class CircuitOrderServiceImpl implements CircuitOrderService {

    private static final Logger log = LoggerFactory.getLogger(CircuitOrderServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CircuitOrderRepository repository;
    private final CircuitOrderMapper mapper;
    private final CircuitOrderValidator validator;
    private final EventPublisher events;

    public CircuitOrderServiceImpl(CircuitOrderRepository repository, CircuitOrderMapper mapper, CircuitOrderValidator validator,
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

    private CircuitOrder load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("CircuitOrder", id));
    }

    @Override
    public CircuitOrderResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CircuitOrderResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("CircuitOrder", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CircuitOrderResponse> search(CircuitOrderSearchCriteria criteria, Pageable pageable) {
        Page<CircuitOrder> page = repository.findAll(CircuitOrderSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CircuitOrderSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CircuitOrderResponse create(CircuitOrderCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("CircuitOrder", "code", request.getCode());
        }
        CircuitOrder entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        CircuitOrder saved = repository.save(entity);
        CircuitOrderResponse response = mapper.toResponse(saved);
        events.publish(CircuitOrderEvents.created(response, actor()));
        log.info("Created CircuitOrder id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CircuitOrderResponse update(UUID id, CircuitOrderUpdateRequest request) {
        CircuitOrder entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "CircuitOrder " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("CircuitOrder", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        CircuitOrder saved = repository.save(entity);
        CircuitOrderResponse response = mapper.toResponse(saved);
        events.publish(CircuitOrderEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CircuitOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CircuitOrderResponse changeStatus(UUID id, LifecycleStatus status) {
        CircuitOrder entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        CircuitOrder saved = repository.save(entity);
        CircuitOrderResponse response = mapper.toResponse(saved);
        events.publish(CircuitOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CircuitOrder entity = load(id);
        CircuitOrderResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CircuitOrderEvents.deleted(snapshot, actor()));
        log.info("Deleted CircuitOrder id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CircuitOrderResponse> bulkCreate(List<CircuitOrderCreateRequest> requests) {
        BulkResult<CircuitOrderResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of CircuitOrder failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CircuitOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CircuitOrderResponse> result = new BulkResult<>();
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
    public void exportCsv(CircuitOrderSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CircuitOrderMapper.CSV_HEADER));
        int page = 0;
        Page<CircuitOrder> chunk;
        do {
            chunk = repository.findAll(CircuitOrderSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CircuitOrderResponse> findByCircuitId(UUID circuitId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCircuitId(tenant(), circuitId, pageable).map(mapper::toResponse));
    }
}
