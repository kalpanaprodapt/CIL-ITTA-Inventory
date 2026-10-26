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
import com.uniti.cil.itta.inventory.circuit.event.CircuitEvents;
import com.uniti.cil.itta.inventory.circuit.mapper.CircuitMapper;
import com.uniti.cil.itta.inventory.circuit.model.Circuit;
import com.uniti.cil.itta.inventory.circuit.repository.CircuitRepository;
import com.uniti.cil.itta.inventory.circuit.spec.CircuitSpecifications;
import com.uniti.cil.itta.inventory.circuit.validation.CircuitValidator;
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
public class CircuitServiceImpl implements CircuitService {

    private static final Logger log = LoggerFactory.getLogger(CircuitServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CircuitRepository repository;
    private final CircuitMapper mapper;
    private final CircuitValidator validator;
    private final EventPublisher events;

    public CircuitServiceImpl(CircuitRepository repository, CircuitMapper mapper, CircuitValidator validator,
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

    private Circuit load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit", id));
    }

    @Override
    public CircuitResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CircuitResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CircuitResponse> search(CircuitSearchCriteria criteria, Pageable pageable) {
        Page<Circuit> page = repository.findAll(CircuitSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CircuitSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CircuitResponse create(CircuitCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Circuit", "code", request.getCode());
        }
        Circuit entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Circuit saved = repository.save(entity);
        CircuitResponse response = mapper.toResponse(saved);
        events.publish(CircuitEvents.created(response, actor()));
        log.info("Created Circuit id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CircuitResponse update(UUID id, CircuitUpdateRequest request) {
        Circuit entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Circuit " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Circuit", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Circuit saved = repository.save(entity);
        CircuitResponse response = mapper.toResponse(saved);
        events.publish(CircuitEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CircuitEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CircuitResponse changeStatus(UUID id, LifecycleStatus status) {
        Circuit entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Circuit saved = repository.save(entity);
        CircuitResponse response = mapper.toResponse(saved);
        events.publish(CircuitEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Circuit entity = load(id);
        CircuitResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CircuitEvents.deleted(snapshot, actor()));
        log.info("Deleted Circuit id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CircuitResponse> bulkCreate(List<CircuitCreateRequest> requests) {
        BulkResult<CircuitResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Circuit failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CircuitResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CircuitResponse> result = new BulkResult<>();
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
    public void exportCsv(CircuitSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CircuitMapper.CSV_HEADER));
        int page = 0;
        Page<Circuit> chunk;
        do {
            chunk = repository.findAll(CircuitSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CircuitResponse> findByProviderId(UUID providerId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndProviderId(tenant(), providerId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<CircuitResponse> findByASiteId(UUID aSiteId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndASiteId(tenant(), aSiteId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<CircuitResponse> findByZSiteId(UUID zSiteId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndZSiteId(tenant(), zSiteId, pageable).map(mapper::toResponse));
    }
}
