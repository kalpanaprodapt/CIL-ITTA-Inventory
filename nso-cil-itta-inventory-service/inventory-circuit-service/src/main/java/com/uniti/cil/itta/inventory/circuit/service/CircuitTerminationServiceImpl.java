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
import com.uniti.cil.itta.inventory.circuit.event.CircuitTerminationEvents;
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitTerminationMapper;
import com.uniti.cil.itta.inventory.circuit.model.CircuitTermination;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitTerminationRepository;
import com.uniti.cil.itta.inventory.circuit.spec.CircuitTerminationSpecifications;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitTerminationValidator;
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
public class CircuitTerminationServiceImpl implements CircuitTerminationService {

    private static final Logger log = LoggerFactory.getLogger(CircuitTerminationServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CircuitTerminationRepository repository;
    private final CircuitTerminationMapper mapper;
    private final CircuitTerminationValidator validator;
    private final EventPublisher events;

    public CircuitTerminationServiceImpl(CircuitTerminationRepository repository, CircuitTerminationMapper mapper, CircuitTerminationValidator validator,
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

    private CircuitTermination load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("CircuitTermination", id));
    }

    @Override
    public CircuitTerminationResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CircuitTerminationResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("CircuitTermination", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CircuitTerminationResponse> search(CircuitTerminationSearchCriteria criteria, Pageable pageable) {
        Page<CircuitTermination> page = repository.findAll(CircuitTerminationSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CircuitTerminationSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CircuitTerminationResponse create(CircuitTerminationCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("CircuitTermination", "code", request.getCode());
        }
        CircuitTermination entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        CircuitTermination saved = repository.save(entity);
        CircuitTerminationResponse response = mapper.toResponse(saved);
        events.publish(CircuitTerminationEvents.created(response, actor()));
        log.info("Created CircuitTermination id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CircuitTerminationResponse update(UUID id, CircuitTerminationUpdateRequest request) {
        CircuitTermination entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "CircuitTermination " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("CircuitTermination", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        CircuitTermination saved = repository.save(entity);
        CircuitTerminationResponse response = mapper.toResponse(saved);
        events.publish(CircuitTerminationEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CircuitTerminationEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CircuitTerminationResponse changeStatus(UUID id, LifecycleStatus status) {
        CircuitTermination entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        CircuitTermination saved = repository.save(entity);
        CircuitTerminationResponse response = mapper.toResponse(saved);
        events.publish(CircuitTerminationEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CircuitTermination entity = load(id);
        CircuitTerminationResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CircuitTerminationEvents.deleted(snapshot, actor()));
        log.info("Deleted CircuitTermination id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CircuitTerminationResponse> bulkCreate(List<CircuitTerminationCreateRequest> requests) {
        BulkResult<CircuitTerminationResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of CircuitTermination failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CircuitTerminationResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CircuitTerminationResponse> result = new BulkResult<>();
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
    public void exportCsv(CircuitTerminationSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CircuitTerminationMapper.CSV_HEADER));
        int page = 0;
        Page<CircuitTermination> chunk;
        do {
            chunk = repository.findAll(CircuitTerminationSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CircuitTerminationResponse> findByCircuitId(UUID circuitId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCircuitId(tenant(), circuitId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<CircuitTerminationResponse> findByPortId(UUID portId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPortId(tenant(), portId, pageable).map(mapper::toResponse));
    }
}
