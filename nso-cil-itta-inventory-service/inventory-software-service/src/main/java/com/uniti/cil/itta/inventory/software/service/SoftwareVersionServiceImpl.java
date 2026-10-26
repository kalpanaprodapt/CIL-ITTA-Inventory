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
import com.uniti.cil.itta.inventory.software.event.SoftwareVersionEvents;
import com.uniti.cil.itta.inventory.software.mapper.SoftwareVersionMapper;
import com.uniti.cil.itta.inventory.software.model.SoftwareVersion;
import com.uniti.cil.itta.inventory.software.repository.SoftwareVersionRepository;
import com.uniti.cil.itta.inventory.software.spec.SoftwareVersionSpecifications;
import com.uniti.cil.itta.inventory.software.validation.SoftwareVersionValidator;
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
public class SoftwareVersionServiceImpl implements SoftwareVersionService {

    private static final Logger log = LoggerFactory.getLogger(SoftwareVersionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SoftwareVersionRepository repository;
    private final SoftwareVersionMapper mapper;
    private final SoftwareVersionValidator validator;
    private final EventPublisher events;

    public SoftwareVersionServiceImpl(SoftwareVersionRepository repository, SoftwareVersionMapper mapper, SoftwareVersionValidator validator,
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

    private SoftwareVersion load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("SoftwareVersion", id));
    }

    @Override
    public SoftwareVersionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SoftwareVersionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("SoftwareVersion", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SoftwareVersionResponse> search(SoftwareVersionSearchCriteria criteria, Pageable pageable) {
        Page<SoftwareVersion> page = repository.findAll(SoftwareVersionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SoftwareVersionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SoftwareVersionResponse create(SoftwareVersionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("SoftwareVersion", "code", request.getCode());
        }
        SoftwareVersion entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        SoftwareVersion saved = repository.save(entity);
        SoftwareVersionResponse response = mapper.toResponse(saved);
        events.publish(SoftwareVersionEvents.created(response, actor()));
        log.info("Created SoftwareVersion id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SoftwareVersionResponse update(UUID id, SoftwareVersionUpdateRequest request) {
        SoftwareVersion entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "SoftwareVersion " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("SoftwareVersion", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        SoftwareVersion saved = repository.save(entity);
        SoftwareVersionResponse response = mapper.toResponse(saved);
        events.publish(SoftwareVersionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SoftwareVersionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SoftwareVersionResponse changeStatus(UUID id, LifecycleStatus status) {
        SoftwareVersion entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        SoftwareVersion saved = repository.save(entity);
        SoftwareVersionResponse response = mapper.toResponse(saved);
        events.publish(SoftwareVersionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SoftwareVersion entity = load(id);
        SoftwareVersionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SoftwareVersionEvents.deleted(snapshot, actor()));
        log.info("Deleted SoftwareVersion id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SoftwareVersionResponse> bulkCreate(List<SoftwareVersionCreateRequest> requests) {
        BulkResult<SoftwareVersionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of SoftwareVersion failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SoftwareVersionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SoftwareVersionResponse> result = new BulkResult<>();
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
    public void exportCsv(SoftwareVersionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SoftwareVersionMapper.CSV_HEADER));
        int page = 0;
        Page<SoftwareVersion> chunk;
        do {
            chunk = repository.findAll(SoftwareVersionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SoftwareVersionResponse> findByImageId(UUID imageId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndImageId(tenant(), imageId, pageable).map(mapper::toResponse));
    }
}
