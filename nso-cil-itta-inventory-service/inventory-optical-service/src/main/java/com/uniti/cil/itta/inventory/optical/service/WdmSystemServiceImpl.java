package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.event.WdmSystemEvents;
import com.uniti.cil.itta.inventory.optical.mapper.WdmSystemMapper;
import com.uniti.cil.itta.inventory.optical.model.WdmSystem;
import com.uniti.cil.itta.inventory.optical.repository.WdmSystemRepository;
import com.uniti.cil.itta.inventory.optical.spec.WdmSystemSpecifications;
import com.uniti.cil.itta.inventory.optical.validation.WdmSystemValidator;
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
public class WdmSystemServiceImpl implements WdmSystemService {

    private static final Logger log = LoggerFactory.getLogger(WdmSystemServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final WdmSystemRepository repository;
    private final WdmSystemMapper mapper;
    private final WdmSystemValidator validator;
    private final EventPublisher events;

    public WdmSystemServiceImpl(WdmSystemRepository repository, WdmSystemMapper mapper, WdmSystemValidator validator,
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

    private WdmSystem load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("WdmSystem", id));
    }

    @Override
    public WdmSystemResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public WdmSystemResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("WdmSystem", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<WdmSystemResponse> search(WdmSystemSearchCriteria criteria, Pageable pageable) {
        Page<WdmSystem> page = repository.findAll(WdmSystemSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<WdmSystemSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public WdmSystemResponse create(WdmSystemCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("WdmSystem", "code", request.getCode());
        }
        WdmSystem entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        WdmSystem saved = repository.save(entity);
        WdmSystemResponse response = mapper.toResponse(saved);
        events.publish(WdmSystemEvents.created(response, actor()));
        log.info("Created WdmSystem id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public WdmSystemResponse update(UUID id, WdmSystemUpdateRequest request) {
        WdmSystem entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "WdmSystem " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("WdmSystem", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        WdmSystem saved = repository.save(entity);
        WdmSystemResponse response = mapper.toResponse(saved);
        events.publish(WdmSystemEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(WdmSystemEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public WdmSystemResponse changeStatus(UUID id, LifecycleStatus status) {
        WdmSystem entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        WdmSystem saved = repository.save(entity);
        WdmSystemResponse response = mapper.toResponse(saved);
        events.publish(WdmSystemEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        WdmSystem entity = load(id);
        WdmSystemResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(WdmSystemEvents.deleted(snapshot, actor()));
        log.info("Deleted WdmSystem id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<WdmSystemResponse> bulkCreate(List<WdmSystemCreateRequest> requests) {
        BulkResult<WdmSystemResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of WdmSystem failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<WdmSystemResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<WdmSystemResponse> result = new BulkResult<>();
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
    public void exportCsv(WdmSystemSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(WdmSystemMapper.CSV_HEADER));
        int page = 0;
        Page<WdmSystem> chunk;
        do {
            chunk = repository.findAll(WdmSystemSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<WdmSystemResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
