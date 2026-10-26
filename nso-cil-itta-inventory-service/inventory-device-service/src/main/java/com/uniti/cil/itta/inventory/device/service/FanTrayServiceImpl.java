package com.uniti.cil.itta.inventory.device.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.event.FanTrayEvents;
import com.uniti.cil.itta.inventory.device.mapper.FanTrayMapper;
import com.uniti.cil.itta.inventory.device.model.FanTray;
import com.uniti.cil.itta.inventory.device.repository.FanTrayRepository;
import com.uniti.cil.itta.inventory.device.spec.FanTraySpecifications;
import com.uniti.cil.itta.inventory.device.validation.FanTrayValidator;
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
public class FanTrayServiceImpl implements FanTrayService {

    private static final Logger log = LoggerFactory.getLogger(FanTrayServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final FanTrayRepository repository;
    private final FanTrayMapper mapper;
    private final FanTrayValidator validator;
    private final EventPublisher events;

    public FanTrayServiceImpl(FanTrayRepository repository, FanTrayMapper mapper, FanTrayValidator validator,
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

    private FanTray load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("FanTray", id));
    }

    @Override
    public FanTrayResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public FanTrayResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("FanTray", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<FanTrayResponse> search(FanTraySearchCriteria criteria, Pageable pageable) {
        Page<FanTray> page = repository.findAll(FanTraySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<FanTraySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public FanTrayResponse create(FanTrayCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("FanTray", "code", request.getCode());
        }
        FanTray entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        FanTray saved = repository.save(entity);
        FanTrayResponse response = mapper.toResponse(saved);
        events.publish(FanTrayEvents.created(response, actor()));
        log.info("Created FanTray id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public FanTrayResponse update(UUID id, FanTrayUpdateRequest request) {
        FanTray entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "FanTray " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("FanTray", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        FanTray saved = repository.save(entity);
        FanTrayResponse response = mapper.toResponse(saved);
        events.publish(FanTrayEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(FanTrayEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public FanTrayResponse changeStatus(UUID id, LifecycleStatus status) {
        FanTray entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        FanTray saved = repository.save(entity);
        FanTrayResponse response = mapper.toResponse(saved);
        events.publish(FanTrayEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        FanTray entity = load(id);
        FanTrayResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(FanTrayEvents.deleted(snapshot, actor()));
        log.info("Deleted FanTray id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<FanTrayResponse> bulkCreate(List<FanTrayCreateRequest> requests) {
        BulkResult<FanTrayResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of FanTray failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<FanTrayResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<FanTrayResponse> result = new BulkResult<>();
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
    public void exportCsv(FanTraySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(FanTrayMapper.CSV_HEADER));
        int page = 0;
        Page<FanTray> chunk;
        do {
            chunk = repository.findAll(FanTraySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<FanTrayResponse> findByChassisId(UUID chassisId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndChassisId(tenant(), chassisId, pageable).map(mapper::toResponse));
    }
}
