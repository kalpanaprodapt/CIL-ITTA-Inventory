package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.event.RoadmEvents;
import com.uniti.cil.itta.inventory.optical.mapper.RoadmMapper;
import com.uniti.cil.itta.inventory.optical.model.Roadm;
import com.uniti.cil.itta.inventory.optical.repository.RoadmRepository;
import com.uniti.cil.itta.inventory.optical.spec.RoadmSpecifications;
import com.uniti.cil.itta.inventory.optical.validation.RoadmValidator;
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
public class RoadmServiceImpl implements RoadmService {

    private static final Logger log = LoggerFactory.getLogger(RoadmServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RoadmRepository repository;
    private final RoadmMapper mapper;
    private final RoadmValidator validator;
    private final EventPublisher events;

    public RoadmServiceImpl(RoadmRepository repository, RoadmMapper mapper, RoadmValidator validator,
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

    private Roadm load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Roadm", id));
    }

    @Override
    public RoadmResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RoadmResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Roadm", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RoadmResponse> search(RoadmSearchCriteria criteria, Pageable pageable) {
        Page<Roadm> page = repository.findAll(RoadmSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RoadmSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RoadmResponse create(RoadmCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Roadm", "code", request.getCode());
        }
        Roadm entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Roadm saved = repository.save(entity);
        RoadmResponse response = mapper.toResponse(saved);
        events.publish(RoadmEvents.created(response, actor()));
        log.info("Created Roadm id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RoadmResponse update(UUID id, RoadmUpdateRequest request) {
        Roadm entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Roadm " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Roadm", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Roadm saved = repository.save(entity);
        RoadmResponse response = mapper.toResponse(saved);
        events.publish(RoadmEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RoadmEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RoadmResponse changeStatus(UUID id, LifecycleStatus status) {
        Roadm entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Roadm saved = repository.save(entity);
        RoadmResponse response = mapper.toResponse(saved);
        events.publish(RoadmEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Roadm entity = load(id);
        RoadmResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RoadmEvents.deleted(snapshot, actor()));
        log.info("Deleted Roadm id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RoadmResponse> bulkCreate(List<RoadmCreateRequest> requests) {
        BulkResult<RoadmResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Roadm failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RoadmResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RoadmResponse> result = new BulkResult<>();
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
    public void exportCsv(RoadmSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RoadmMapper.CSV_HEADER));
        int page = 0;
        Page<Roadm> chunk;
        do {
            chunk = repository.findAll(RoadmSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RoadmResponse> findBySystemId(UUID systemId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSystemId(tenant(), systemId, pageable).map(mapper::toResponse));
    }
}
