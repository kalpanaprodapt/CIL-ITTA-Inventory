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
import com.uniti.cil.itta.inventory.software.event.SoftwareImageEvents;
import com.uniti.cil.itta.inventory.software.mapper.SoftwareImageMapper;
import com.uniti.cil.itta.inventory.software.model.SoftwareImage;
import com.uniti.cil.itta.inventory.software.repository.SoftwareImageRepository;
import com.uniti.cil.itta.inventory.software.spec.SoftwareImageSpecifications;
import com.uniti.cil.itta.inventory.software.validation.SoftwareImageValidator;
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
public class SoftwareImageServiceImpl implements SoftwareImageService {

    private static final Logger log = LoggerFactory.getLogger(SoftwareImageServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SoftwareImageRepository repository;
    private final SoftwareImageMapper mapper;
    private final SoftwareImageValidator validator;
    private final EventPublisher events;

    public SoftwareImageServiceImpl(SoftwareImageRepository repository, SoftwareImageMapper mapper, SoftwareImageValidator validator,
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

    private SoftwareImage load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("SoftwareImage", id));
    }

    @Override
    public SoftwareImageResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SoftwareImageResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("SoftwareImage", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SoftwareImageResponse> search(SoftwareImageSearchCriteria criteria, Pageable pageable) {
        Page<SoftwareImage> page = repository.findAll(SoftwareImageSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SoftwareImageSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SoftwareImageResponse create(SoftwareImageCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("SoftwareImage", "code", request.getCode());
        }
        SoftwareImage entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        SoftwareImage saved = repository.save(entity);
        SoftwareImageResponse response = mapper.toResponse(saved);
        events.publish(SoftwareImageEvents.created(response, actor()));
        log.info("Created SoftwareImage id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SoftwareImageResponse update(UUID id, SoftwareImageUpdateRequest request) {
        SoftwareImage entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "SoftwareImage " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("SoftwareImage", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        SoftwareImage saved = repository.save(entity);
        SoftwareImageResponse response = mapper.toResponse(saved);
        events.publish(SoftwareImageEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SoftwareImageEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SoftwareImageResponse changeStatus(UUID id, LifecycleStatus status) {
        SoftwareImage entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        SoftwareImage saved = repository.save(entity);
        SoftwareImageResponse response = mapper.toResponse(saved);
        events.publish(SoftwareImageEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SoftwareImage entity = load(id);
        SoftwareImageResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SoftwareImageEvents.deleted(snapshot, actor()));
        log.info("Deleted SoftwareImage id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SoftwareImageResponse> bulkCreate(List<SoftwareImageCreateRequest> requests) {
        BulkResult<SoftwareImageResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of SoftwareImage failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SoftwareImageResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SoftwareImageResponse> result = new BulkResult<>();
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
    public void exportCsv(SoftwareImageSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SoftwareImageMapper.CSV_HEADER));
        int page = 0;
        Page<SoftwareImage> chunk;
        do {
            chunk = repository.findAll(SoftwareImageSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SoftwareImageResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
