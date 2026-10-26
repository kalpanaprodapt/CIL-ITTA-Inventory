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
import com.uniti.cil.itta.inventory.software.event.LicenseKeyEvents;
import com.uniti.cil.itta.inventory.software.mapper.LicenseKeyMapper;
import com.uniti.cil.itta.inventory.software.model.LicenseKey;
import com.uniti.cil.itta.inventory.software.repository.LicenseKeyRepository;
import com.uniti.cil.itta.inventory.software.spec.LicenseKeySpecifications;
import com.uniti.cil.itta.inventory.software.validation.LicenseKeyValidator;
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
public class LicenseKeyServiceImpl implements LicenseKeyService {

    private static final Logger log = LoggerFactory.getLogger(LicenseKeyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final LicenseKeyRepository repository;
    private final LicenseKeyMapper mapper;
    private final LicenseKeyValidator validator;
    private final EventPublisher events;

    public LicenseKeyServiceImpl(LicenseKeyRepository repository, LicenseKeyMapper mapper, LicenseKeyValidator validator,
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

    private LicenseKey load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("LicenseKey", id));
    }

    @Override
    public LicenseKeyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public LicenseKeyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("LicenseKey", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<LicenseKeyResponse> search(LicenseKeySearchCriteria criteria, Pageable pageable) {
        Page<LicenseKey> page = repository.findAll(LicenseKeySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<LicenseKeySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public LicenseKeyResponse create(LicenseKeyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("LicenseKey", "code", request.getCode());
        }
        LicenseKey entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        LicenseKey saved = repository.save(entity);
        LicenseKeyResponse response = mapper.toResponse(saved);
        events.publish(LicenseKeyEvents.created(response, actor()));
        log.info("Created LicenseKey id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public LicenseKeyResponse update(UUID id, LicenseKeyUpdateRequest request) {
        LicenseKey entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "LicenseKey " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("LicenseKey", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        LicenseKey saved = repository.save(entity);
        LicenseKeyResponse response = mapper.toResponse(saved);
        events.publish(LicenseKeyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(LicenseKeyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public LicenseKeyResponse changeStatus(UUID id, LifecycleStatus status) {
        LicenseKey entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        LicenseKey saved = repository.save(entity);
        LicenseKeyResponse response = mapper.toResponse(saved);
        events.publish(LicenseKeyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        LicenseKey entity = load(id);
        LicenseKeyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(LicenseKeyEvents.deleted(snapshot, actor()));
        log.info("Deleted LicenseKey id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<LicenseKeyResponse> bulkCreate(List<LicenseKeyCreateRequest> requests) {
        BulkResult<LicenseKeyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of LicenseKey failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<LicenseKeyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<LicenseKeyResponse> result = new BulkResult<>();
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
    public void exportCsv(LicenseKeySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(LicenseKeyMapper.CSV_HEADER));
        int page = 0;
        Page<LicenseKey> chunk;
        do {
            chunk = repository.findAll(LicenseKeySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<LicenseKeyResponse> findByPoolId(UUID poolId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPoolId(tenant(), poolId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<LicenseKeyResponse> findByAssignedDeviceId(UUID assignedDeviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssignedDeviceId(tenant(), assignedDeviceId, pageable).map(mapper::toResponse));
    }
}
