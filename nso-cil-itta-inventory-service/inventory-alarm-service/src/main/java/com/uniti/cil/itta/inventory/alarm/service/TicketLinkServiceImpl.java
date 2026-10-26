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
import com.uniti.cil.itta.inventory.alarm.event.TicketLinkEvents;
import com.uniti.cil.itta.inventory.alarm.mapper.TicketLinkMapper;
import com.uniti.cil.itta.inventory.alarm.model.TicketLink;
import com.uniti.cil.itta.inventory.alarm.repository.TicketLinkRepository;
import com.uniti.cil.itta.inventory.alarm.spec.TicketLinkSpecifications;
import com.uniti.cil.itta.inventory.alarm.validation.TicketLinkValidator;
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
public class TicketLinkServiceImpl implements TicketLinkService {

    private static final Logger log = LoggerFactory.getLogger(TicketLinkServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final TicketLinkRepository repository;
    private final TicketLinkMapper mapper;
    private final TicketLinkValidator validator;
    private final EventPublisher events;

    public TicketLinkServiceImpl(TicketLinkRepository repository, TicketLinkMapper mapper, TicketLinkValidator validator,
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

    private TicketLink load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("TicketLink", id));
    }

    @Override
    public TicketLinkResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public TicketLinkResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("TicketLink", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<TicketLinkResponse> search(TicketLinkSearchCriteria criteria, Pageable pageable) {
        Page<TicketLink> page = repository.findAll(TicketLinkSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<TicketLinkSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public TicketLinkResponse create(TicketLinkCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("TicketLink", "code", request.getCode());
        }
        TicketLink entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        TicketLink saved = repository.save(entity);
        TicketLinkResponse response = mapper.toResponse(saved);
        events.publish(TicketLinkEvents.created(response, actor()));
        log.info("Created TicketLink id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public TicketLinkResponse update(UUID id, TicketLinkUpdateRequest request) {
        TicketLink entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "TicketLink " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("TicketLink", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        TicketLink saved = repository.save(entity);
        TicketLinkResponse response = mapper.toResponse(saved);
        events.publish(TicketLinkEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(TicketLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public TicketLinkResponse changeStatus(UUID id, LifecycleStatus status) {
        TicketLink entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        TicketLink saved = repository.save(entity);
        TicketLinkResponse response = mapper.toResponse(saved);
        events.publish(TicketLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TicketLink entity = load(id);
        TicketLinkResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(TicketLinkEvents.deleted(snapshot, actor()));
        log.info("Deleted TicketLink id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<TicketLinkResponse> bulkCreate(List<TicketLinkCreateRequest> requests) {
        BulkResult<TicketLinkResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of TicketLink failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<TicketLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<TicketLinkResponse> result = new BulkResult<>();
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
    public void exportCsv(TicketLinkSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(TicketLinkMapper.CSV_HEADER));
        int page = 0;
        Page<TicketLink> chunk;
        do {
            chunk = repository.findAll(TicketLinkSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<TicketLinkResponse> findByAlarmId(UUID alarmId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAlarmId(tenant(), alarmId, pageable).map(mapper::toResponse));
    }
}
