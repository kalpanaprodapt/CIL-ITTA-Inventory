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
import com.uniti.cil.itta.inventory.software.event.LicensePoolEvents;
import com.uniti.cil.itta.inventory.software.mapper.LicensePoolMapper;
import com.uniti.cil.itta.inventory.software.model.LicensePool;
import com.uniti.cil.itta.inventory.software.repository.LicensePoolRepository;
import com.uniti.cil.itta.inventory.software.spec.LicensePoolSpecifications;
import com.uniti.cil.itta.inventory.software.validation.LicensePoolValidator;
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
public class LicensePoolServiceImpl implements LicensePoolService {

    private static final Logger log = LoggerFactory.getLogger(LicensePoolServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final LicensePoolRepository repository;
    private final LicensePoolMapper mapper;
    private final LicensePoolValidator validator;
    private final EventPublisher events;

    public LicensePoolServiceImpl(LicensePoolRepository repository, LicensePoolMapper mapper, LicensePoolValidator validator,
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

    private LicensePool load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("LicensePool", id));
    }

    @Override
    public LicensePoolResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public LicensePoolResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("LicensePool", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<LicensePoolResponse> search(LicensePoolSearchCriteria criteria, Pageable pageable) {
        Page<LicensePool> page = repository.findAll(LicensePoolSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<LicensePoolSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public LicensePoolResponse create(LicensePoolCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("LicensePool", "code", request.getCode());
        }
        LicensePool entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        LicensePool saved = repository.save(entity);
        LicensePoolResponse response = mapper.toResponse(saved);
        events.publish(LicensePoolEvents.created(response, actor()));
        log.info("Created LicensePool id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public LicensePoolResponse update(UUID id, LicensePoolUpdateRequest request) {
        LicensePool entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "LicensePool " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("LicensePool", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        LicensePool saved = repository.save(entity);
        LicensePoolResponse response = mapper.toResponse(saved);
        events.publish(LicensePoolEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(LicensePoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public LicensePoolResponse changeStatus(UUID id, LifecycleStatus status) {
        LicensePool entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        LicensePool saved = repository.save(entity);
        LicensePoolResponse response = mapper.toResponse(saved);
        events.publish(LicensePoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        LicensePool entity = load(id);
        LicensePoolResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(LicensePoolEvents.deleted(snapshot, actor()));
        log.info("Deleted LicensePool id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<LicensePoolResponse> bulkCreate(List<LicensePoolCreateRequest> requests) {
        BulkResult<LicensePoolResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of LicensePool failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<LicensePoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<LicensePoolResponse> result = new BulkResult<>();
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
    public void exportCsv(LicensePoolSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(LicensePoolMapper.CSV_HEADER));
        int page = 0;
        Page<LicensePool> chunk;
        do {
            chunk = repository.findAll(LicensePoolSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<LicensePoolResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
