package com.uniti.cil.itta.inventory.facility.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.event.RackEvents;
import com.uniti.cil.itta.inventory.facility.mapper.RackMapper;
import com.uniti.cil.itta.inventory.facility.model.Rack;
import com.uniti.cil.itta.inventory.facility.repository.RackRepository;
import com.uniti.cil.itta.inventory.facility.spec.RackSpecifications;
import com.uniti.cil.itta.inventory.facility.validation.RackValidator;
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
public class RackServiceImpl implements RackService {

    private static final Logger log = LoggerFactory.getLogger(RackServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RackRepository repository;
    private final RackMapper mapper;
    private final RackValidator validator;
    private final EventPublisher events;

    public RackServiceImpl(RackRepository repository, RackMapper mapper, RackValidator validator,
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

    private Rack load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Rack", id));
    }

    @Override
    public RackResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RackResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Rack", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RackResponse> search(RackSearchCriteria criteria, Pageable pageable) {
        Page<Rack> page = repository.findAll(RackSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RackSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RackResponse create(RackCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Rack", "code", request.getCode());
        }
        Rack entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Rack saved = repository.save(entity);
        RackResponse response = mapper.toResponse(saved);
        events.publish(RackEvents.created(response, actor()));
        log.info("Created Rack id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RackResponse update(UUID id, RackUpdateRequest request) {
        Rack entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Rack " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Rack", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Rack saved = repository.save(entity);
        RackResponse response = mapper.toResponse(saved);
        events.publish(RackEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RackEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RackResponse changeStatus(UUID id, LifecycleStatus status) {
        Rack entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Rack saved = repository.save(entity);
        RackResponse response = mapper.toResponse(saved);
        events.publish(RackEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Rack entity = load(id);
        RackResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RackEvents.deleted(snapshot, actor()));
        log.info("Deleted Rack id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RackResponse> bulkCreate(List<RackCreateRequest> requests) {
        BulkResult<RackResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Rack failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RackResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RackResponse> result = new BulkResult<>();
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
    public void exportCsv(RackSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RackMapper.CSV_HEADER));
        int page = 0;
        Page<Rack> chunk;
        do {
            chunk = repository.findAll(RackSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RackResponse> findByRoomId(UUID roomId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRoomId(tenant(), roomId, pageable).map(mapper::toResponse));
    }
}
