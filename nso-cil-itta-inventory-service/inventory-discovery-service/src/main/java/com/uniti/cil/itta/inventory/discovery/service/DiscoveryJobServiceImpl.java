package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.event.DiscoveryJobEvents;
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveryJobMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryJob;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveryJobRepository;
import com.uniti.cil.itta.inventory.discovery.spec.DiscoveryJobSpecifications;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveryJobValidator;
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
public class DiscoveryJobServiceImpl implements DiscoveryJobService {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryJobServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final DiscoveryJobRepository repository;
    private final DiscoveryJobMapper mapper;
    private final DiscoveryJobValidator validator;
    private final EventPublisher events;

    public DiscoveryJobServiceImpl(DiscoveryJobRepository repository, DiscoveryJobMapper mapper, DiscoveryJobValidator validator,
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

    private DiscoveryJob load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveryJob", id));
    }

    @Override
    public DiscoveryJobResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public DiscoveryJobResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveryJob", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<DiscoveryJobResponse> search(DiscoveryJobSearchCriteria criteria, Pageable pageable) {
        Page<DiscoveryJob> page = repository.findAll(DiscoveryJobSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<DiscoveryJobSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public DiscoveryJobResponse create(DiscoveryJobCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("DiscoveryJob", "code", request.getCode());
        }
        DiscoveryJob entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        DiscoveryJob saved = repository.save(entity);
        DiscoveryJobResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryJobEvents.created(response, actor()));
        log.info("Created DiscoveryJob id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public DiscoveryJobResponse update(UUID id, DiscoveryJobUpdateRequest request) {
        DiscoveryJob entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "DiscoveryJob " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("DiscoveryJob", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        DiscoveryJob saved = repository.save(entity);
        DiscoveryJobResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryJobEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(DiscoveryJobEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public DiscoveryJobResponse changeStatus(UUID id, LifecycleStatus status) {
        DiscoveryJob entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        DiscoveryJob saved = repository.save(entity);
        DiscoveryJobResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryJobEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DiscoveryJob entity = load(id);
        DiscoveryJobResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(DiscoveryJobEvents.deleted(snapshot, actor()));
        log.info("Deleted DiscoveryJob id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<DiscoveryJobResponse> bulkCreate(List<DiscoveryJobCreateRequest> requests) {
        BulkResult<DiscoveryJobResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of DiscoveryJob failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<DiscoveryJobResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<DiscoveryJobResponse> result = new BulkResult<>();
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
    public void exportCsv(DiscoveryJobSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(DiscoveryJobMapper.CSV_HEADER));
        int page = 0;
        Page<DiscoveryJob> chunk;
        do {
            chunk = repository.findAll(DiscoveryJobSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<DiscoveryJobResponse> findByProfileId(UUID profileId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndProfileId(tenant(), profileId, pageable).map(mapper::toResponse));
    }
}
