package com.uniti.cil.itta.inventory.assetmgmt.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.event.DepreciationScheduleEvents;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.DepreciationScheduleMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.DepreciationSchedule;
import com.uniti.cil.itta.inventory.assetmgmt.repository.DepreciationScheduleRepository;
import com.uniti.cil.itta.inventory.assetmgmt.spec.DepreciationScheduleSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.validation.DepreciationScheduleValidator;
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
public class DepreciationScheduleServiceImpl implements DepreciationScheduleService {

    private static final Logger log = LoggerFactory.getLogger(DepreciationScheduleServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final DepreciationScheduleRepository repository;
    private final DepreciationScheduleMapper mapper;
    private final DepreciationScheduleValidator validator;
    private final EventPublisher events;

    public DepreciationScheduleServiceImpl(DepreciationScheduleRepository repository, DepreciationScheduleMapper mapper, DepreciationScheduleValidator validator,
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

    private DepreciationSchedule load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("DepreciationSchedule", id));
    }

    @Override
    public DepreciationScheduleResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public DepreciationScheduleResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DepreciationSchedule", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<DepreciationScheduleResponse> search(DepreciationScheduleSearchCriteria criteria, Pageable pageable) {
        Page<DepreciationSchedule> page = repository.findAll(DepreciationScheduleSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<DepreciationScheduleSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public DepreciationScheduleResponse create(DepreciationScheduleCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("DepreciationSchedule", "code", request.getCode());
        }
        DepreciationSchedule entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        DepreciationSchedule saved = repository.save(entity);
        DepreciationScheduleResponse response = mapper.toResponse(saved);
        events.publish(DepreciationScheduleEvents.created(response, actor()));
        log.info("Created DepreciationSchedule id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public DepreciationScheduleResponse update(UUID id, DepreciationScheduleUpdateRequest request) {
        DepreciationSchedule entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "DepreciationSchedule " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("DepreciationSchedule", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        DepreciationSchedule saved = repository.save(entity);
        DepreciationScheduleResponse response = mapper.toResponse(saved);
        events.publish(DepreciationScheduleEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(DepreciationScheduleEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public DepreciationScheduleResponse changeStatus(UUID id, LifecycleStatus status) {
        DepreciationSchedule entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        DepreciationSchedule saved = repository.save(entity);
        DepreciationScheduleResponse response = mapper.toResponse(saved);
        events.publish(DepreciationScheduleEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DepreciationSchedule entity = load(id);
        DepreciationScheduleResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(DepreciationScheduleEvents.deleted(snapshot, actor()));
        log.info("Deleted DepreciationSchedule id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<DepreciationScheduleResponse> bulkCreate(List<DepreciationScheduleCreateRequest> requests) {
        BulkResult<DepreciationScheduleResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of DepreciationSchedule failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<DepreciationScheduleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<DepreciationScheduleResponse> result = new BulkResult<>();
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
    public void exportCsv(DepreciationScheduleSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(DepreciationScheduleMapper.CSV_HEADER));
        int page = 0;
        Page<DepreciationSchedule> chunk;
        do {
            chunk = repository.findAll(DepreciationScheduleSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<DepreciationScheduleResponse> findByAssetId(UUID assetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssetId(tenant(), assetId, pageable).map(mapper::toResponse));
    }
}
