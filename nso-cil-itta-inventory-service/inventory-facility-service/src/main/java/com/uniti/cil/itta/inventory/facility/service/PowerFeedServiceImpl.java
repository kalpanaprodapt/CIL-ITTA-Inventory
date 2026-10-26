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
import com.uniti.cil.itta.inventory.facility.event.PowerFeedEvents;
import com.uniti.cil.itta.inventory.facility.mapper.PowerFeedMapper;
import com.uniti.cil.itta.inventory.facility.model.PowerFeed;
import com.uniti.cil.itta.inventory.facility.repository.PowerFeedRepository;
import com.uniti.cil.itta.inventory.facility.spec.PowerFeedSpecifications;
import com.uniti.cil.itta.inventory.facility.validation.PowerFeedValidator;
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
public class PowerFeedServiceImpl implements PowerFeedService {

    private static final Logger log = LoggerFactory.getLogger(PowerFeedServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PowerFeedRepository repository;
    private final PowerFeedMapper mapper;
    private final PowerFeedValidator validator;
    private final EventPublisher events;

    public PowerFeedServiceImpl(PowerFeedRepository repository, PowerFeedMapper mapper, PowerFeedValidator validator,
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

    private PowerFeed load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PowerFeed", id));
    }

    @Override
    public PowerFeedResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PowerFeedResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PowerFeed", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PowerFeedResponse> search(PowerFeedSearchCriteria criteria, Pageable pageable) {
        Page<PowerFeed> page = repository.findAll(PowerFeedSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PowerFeedSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PowerFeedResponse create(PowerFeedCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PowerFeed", "code", request.getCode());
        }
        PowerFeed entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PowerFeed saved = repository.save(entity);
        PowerFeedResponse response = mapper.toResponse(saved);
        events.publish(PowerFeedEvents.created(response, actor()));
        log.info("Created PowerFeed id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PowerFeedResponse update(UUID id, PowerFeedUpdateRequest request) {
        PowerFeed entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PowerFeed " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PowerFeed", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PowerFeed saved = repository.save(entity);
        PowerFeedResponse response = mapper.toResponse(saved);
        events.publish(PowerFeedEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PowerFeedEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PowerFeedResponse changeStatus(UUID id, LifecycleStatus status) {
        PowerFeed entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PowerFeed saved = repository.save(entity);
        PowerFeedResponse response = mapper.toResponse(saved);
        events.publish(PowerFeedEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PowerFeed entity = load(id);
        PowerFeedResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PowerFeedEvents.deleted(snapshot, actor()));
        log.info("Deleted PowerFeed id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PowerFeedResponse> bulkCreate(List<PowerFeedCreateRequest> requests) {
        BulkResult<PowerFeedResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PowerFeed failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PowerFeedResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PowerFeedResponse> result = new BulkResult<>();
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
    public void exportCsv(PowerFeedSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PowerFeedMapper.CSV_HEADER));
        int page = 0;
        Page<PowerFeed> chunk;
        do {
            chunk = repository.findAll(PowerFeedSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PowerFeedResponse> findByRoomId(UUID roomId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRoomId(tenant(), roomId, pageable).map(mapper::toResponse));
    }
}
