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
import com.uniti.cil.itta.inventory.change.event.WorkOrderEvents;
import com.uniti.cil.itta.inventory.change.mapper.WorkOrderMapper;
import com.uniti.cil.itta.inventory.change.model.WorkOrder;
import com.uniti.cil.itta.inventory.change.repository.WorkOrderRepository;
import com.uniti.cil.itta.inventory.change.spec.WorkOrderSpecifications;
import com.uniti.cil.itta.inventory.change.validation.WorkOrderValidator;
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
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final WorkOrderRepository repository;
    private final WorkOrderMapper mapper;
    private final WorkOrderValidator validator;
    private final EventPublisher events;

    public WorkOrderServiceImpl(WorkOrderRepository repository, WorkOrderMapper mapper, WorkOrderValidator validator,
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

    private WorkOrder load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkOrder", id));
    }

    @Override
    public WorkOrderResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public WorkOrderResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("WorkOrder", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<WorkOrderResponse> search(WorkOrderSearchCriteria criteria, Pageable pageable) {
        Page<WorkOrder> page = repository.findAll(WorkOrderSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<WorkOrderSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public WorkOrderResponse create(WorkOrderCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("WorkOrder", "code", request.getCode());
        }
        WorkOrder entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        WorkOrder saved = repository.save(entity);
        WorkOrderResponse response = mapper.toResponse(saved);
        events.publish(WorkOrderEvents.created(response, actor()));
        log.info("Created WorkOrder id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public WorkOrderResponse update(UUID id, WorkOrderUpdateRequest request) {
        WorkOrder entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "WorkOrder " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("WorkOrder", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        WorkOrder saved = repository.save(entity);
        WorkOrderResponse response = mapper.toResponse(saved);
        events.publish(WorkOrderEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(WorkOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public WorkOrderResponse changeStatus(UUID id, LifecycleStatus status) {
        WorkOrder entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        WorkOrder saved = repository.save(entity);
        WorkOrderResponse response = mapper.toResponse(saved);
        events.publish(WorkOrderEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        WorkOrder entity = load(id);
        WorkOrderResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(WorkOrderEvents.deleted(snapshot, actor()));
        log.info("Deleted WorkOrder id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<WorkOrderResponse> bulkCreate(List<WorkOrderCreateRequest> requests) {
        BulkResult<WorkOrderResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of WorkOrder failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<WorkOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<WorkOrderResponse> result = new BulkResult<>();
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
    public void exportCsv(WorkOrderSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(WorkOrderMapper.CSV_HEADER));
        int page = 0;
        Page<WorkOrder> chunk;
        do {
            chunk = repository.findAll(WorkOrderSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<WorkOrderResponse> findByAssignedTechnicianId(UUID assignedTechnicianId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssignedTechnicianId(tenant(), assignedTechnicianId, pageable).map(mapper::toResponse));
    }
}
