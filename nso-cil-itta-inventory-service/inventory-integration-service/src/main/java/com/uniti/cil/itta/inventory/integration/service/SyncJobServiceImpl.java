package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.event.SyncJobEvents;
import com.uniti.cil.itta.inventory.integration.mapper.SyncJobMapper;
import com.uniti.cil.itta.inventory.integration.model.SyncJob;
import com.uniti.cil.itta.inventory.integration.repository.SyncJobRepository;
import com.uniti.cil.itta.inventory.integration.spec.SyncJobSpecifications;
import com.uniti.cil.itta.inventory.integration.validation.SyncJobValidator;
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
public class SyncJobServiceImpl implements SyncJobService {

    private static final Logger log = LoggerFactory.getLogger(SyncJobServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SyncJobRepository repository;
    private final SyncJobMapper mapper;
    private final SyncJobValidator validator;
    private final EventPublisher events;

    public SyncJobServiceImpl(SyncJobRepository repository, SyncJobMapper mapper, SyncJobValidator validator,
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

    private SyncJob load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("SyncJob", id));
    }

    @Override
    public SyncJobResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SyncJobResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("SyncJob", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SyncJobResponse> search(SyncJobSearchCriteria criteria, Pageable pageable) {
        Page<SyncJob> page = repository.findAll(SyncJobSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SyncJobSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SyncJobResponse create(SyncJobCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("SyncJob", "code", request.getCode());
        }
        SyncJob entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        SyncJob saved = repository.save(entity);
        SyncJobResponse response = mapper.toResponse(saved);
        events.publish(SyncJobEvents.created(response, actor()));
        log.info("Created SyncJob id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SyncJobResponse update(UUID id, SyncJobUpdateRequest request) {
        SyncJob entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "SyncJob " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("SyncJob", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        SyncJob saved = repository.save(entity);
        SyncJobResponse response = mapper.toResponse(saved);
        events.publish(SyncJobEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SyncJobEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SyncJobResponse changeStatus(UUID id, LifecycleStatus status) {
        SyncJob entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        SyncJob saved = repository.save(entity);
        SyncJobResponse response = mapper.toResponse(saved);
        events.publish(SyncJobEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SyncJob entity = load(id);
        SyncJobResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SyncJobEvents.deleted(snapshot, actor()));
        log.info("Deleted SyncJob id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SyncJobResponse> bulkCreate(List<SyncJobCreateRequest> requests) {
        BulkResult<SyncJobResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of SyncJob failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SyncJobResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SyncJobResponse> result = new BulkResult<>();
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
    public void exportCsv(SyncJobSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SyncJobMapper.CSV_HEADER));
        int page = 0;
        Page<SyncJob> chunk;
        do {
            chunk = repository.findAll(SyncJobSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SyncJobResponse> findByEndpointId(UUID endpointId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndEndpointId(tenant(), endpointId, pageable).map(mapper::toResponse));
    }
}
