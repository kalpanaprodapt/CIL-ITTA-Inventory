package com.uniti.cil.itta.inventory.software.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.event.PatchEvents;
import com.uniti.cil.itta.inventory.software.mapper.PatchMapper;
import com.uniti.cil.itta.inventory.software.model.Patch;
import com.uniti.cil.itta.inventory.software.repository.PatchRepository;
import com.uniti.cil.itta.inventory.software.spec.PatchSpecifications;
import com.uniti.cil.itta.inventory.software.validation.PatchValidator;
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
public class PatchServiceImpl implements PatchService {

    private static final Logger log = LoggerFactory.getLogger(PatchServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PatchRepository repository;
    private final PatchMapper mapper;
    private final PatchValidator validator;
    private final EventPublisher events;

    public PatchServiceImpl(PatchRepository repository, PatchMapper mapper, PatchValidator validator,
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

    private Patch load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Patch", id));
    }

    @Override
    public PatchResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PatchResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Patch", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PatchResponse> search(PatchSearchCriteria criteria, Pageable pageable) {
        Page<Patch> page = repository.findAll(PatchSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PatchSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PatchResponse create(PatchCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Patch", "code", request.getCode());
        }
        Patch entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Patch saved = repository.save(entity);
        PatchResponse response = mapper.toResponse(saved);
        events.publish(PatchEvents.created(response, actor()));
        log.info("Created Patch id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PatchResponse update(UUID id, PatchUpdateRequest request) {
        Patch entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Patch " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Patch", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Patch saved = repository.save(entity);
        PatchResponse response = mapper.toResponse(saved);
        events.publish(PatchEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PatchEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PatchResponse changeStatus(UUID id, LifecycleStatus status) {
        Patch entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Patch saved = repository.save(entity);
        PatchResponse response = mapper.toResponse(saved);
        events.publish(PatchEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Patch entity = load(id);
        PatchResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PatchEvents.deleted(snapshot, actor()));
        log.info("Deleted Patch id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PatchResponse> bulkCreate(List<PatchCreateRequest> requests) {
        BulkResult<PatchResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Patch failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PatchResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PatchResponse> result = new BulkResult<>();
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
    public void exportCsv(PatchSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PatchMapper.CSV_HEADER));
        int page = 0;
        Page<Patch> chunk;
        do {
            chunk = repository.findAll(PatchSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PatchResponse> findByVersionId(UUID versionId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVersionId(tenant(), versionId, pageable).map(mapper::toResponse));
    }
}
