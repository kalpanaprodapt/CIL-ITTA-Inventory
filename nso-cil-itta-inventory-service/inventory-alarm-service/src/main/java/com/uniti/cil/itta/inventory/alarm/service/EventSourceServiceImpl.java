package com.uniti.cil.itta.inventory.alarm.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.event.EventSourceEvents;
import com.uniti.cil.itta.inventory.alarm.mapper.EventSourceMapper;
import com.uniti.cil.itta.inventory.alarm.model.EventSource;
import com.uniti.cil.itta.inventory.alarm.repository.EventSourceRepository;
import com.uniti.cil.itta.inventory.alarm.spec.EventSourceSpecifications;
import com.uniti.cil.itta.inventory.alarm.validation.EventSourceValidator;
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
public class EventSourceServiceImpl implements EventSourceService {

    private static final Logger log = LoggerFactory.getLogger(EventSourceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final EventSourceRepository repository;
    private final EventSourceMapper mapper;
    private final EventSourceValidator validator;
    private final EventPublisher events;

    public EventSourceServiceImpl(EventSourceRepository repository, EventSourceMapper mapper, EventSourceValidator validator,
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

    private EventSource load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("EventSource", id));
    }

    @Override
    public EventSourceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public EventSourceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("EventSource", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<EventSourceResponse> search(EventSourceSearchCriteria criteria, Pageable pageable) {
        Page<EventSource> page = repository.findAll(EventSourceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<EventSourceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public EventSourceResponse create(EventSourceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("EventSource", "code", request.getCode());
        }
        EventSource entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        EventSource saved = repository.save(entity);
        EventSourceResponse response = mapper.toResponse(saved);
        events.publish(EventSourceEvents.created(response, actor()));
        log.info("Created EventSource id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public EventSourceResponse update(UUID id, EventSourceUpdateRequest request) {
        EventSource entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "EventSource " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("EventSource", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        EventSource saved = repository.save(entity);
        EventSourceResponse response = mapper.toResponse(saved);
        events.publish(EventSourceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(EventSourceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public EventSourceResponse changeStatus(UUID id, LifecycleStatus status) {
        EventSource entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        EventSource saved = repository.save(entity);
        EventSourceResponse response = mapper.toResponse(saved);
        events.publish(EventSourceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        EventSource entity = load(id);
        EventSourceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(EventSourceEvents.deleted(snapshot, actor()));
        log.info("Deleted EventSource id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<EventSourceResponse> bulkCreate(List<EventSourceCreateRequest> requests) {
        BulkResult<EventSourceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of EventSource failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<EventSourceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<EventSourceResponse> result = new BulkResult<>();
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
    public void exportCsv(EventSourceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(EventSourceMapper.CSV_HEADER));
        int page = 0;
        Page<EventSource> chunk;
        do {
            chunk = repository.findAll(EventSourceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
