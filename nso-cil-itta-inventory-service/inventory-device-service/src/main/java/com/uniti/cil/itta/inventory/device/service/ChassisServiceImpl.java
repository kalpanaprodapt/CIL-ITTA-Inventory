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
import com.uniti.cil.itta.inventory.device.event.ChassisEvents;
import com.uniti.cil.itta.inventory.device.mapper.ChassisMapper;
import com.uniti.cil.itta.inventory.device.model.Chassis;
import com.uniti.cil.itta.inventory.device.repository.ChassisRepository;
import com.uniti.cil.itta.inventory.device.spec.ChassisSpecifications;
import com.uniti.cil.itta.inventory.device.validation.ChassisValidator;
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
public class ChassisServiceImpl implements ChassisService {

    private static final Logger log = LoggerFactory.getLogger(ChassisServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ChassisRepository repository;
    private final ChassisMapper mapper;
    private final ChassisValidator validator;
    private final EventPublisher events;

    public ChassisServiceImpl(ChassisRepository repository, ChassisMapper mapper, ChassisValidator validator,
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

    private Chassis load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Chassis", id));
    }

    @Override
    public ChassisResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ChassisResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Chassis", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ChassisResponse> search(ChassisSearchCriteria criteria, Pageable pageable) {
        Page<Chassis> page = repository.findAll(ChassisSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ChassisSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ChassisResponse create(ChassisCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Chassis", "code", request.getCode());
        }
        Chassis entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Chassis saved = repository.save(entity);
        ChassisResponse response = mapper.toResponse(saved);
        events.publish(ChassisEvents.created(response, actor()));
        log.info("Created Chassis id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ChassisResponse update(UUID id, ChassisUpdateRequest request) {
        Chassis entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Chassis " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Chassis", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Chassis saved = repository.save(entity);
        ChassisResponse response = mapper.toResponse(saved);
        events.publish(ChassisEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ChassisEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ChassisResponse changeStatus(UUID id, LifecycleStatus status) {
        Chassis entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Chassis saved = repository.save(entity);
        ChassisResponse response = mapper.toResponse(saved);
        events.publish(ChassisEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Chassis entity = load(id);
        ChassisResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ChassisEvents.deleted(snapshot, actor()));
        log.info("Deleted Chassis id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ChassisResponse> bulkCreate(List<ChassisCreateRequest> requests) {
        BulkResult<ChassisResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Chassis failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ChassisResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ChassisResponse> result = new BulkResult<>();
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
    public void exportCsv(ChassisSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ChassisMapper.CSV_HEADER));
        int page = 0;
        Page<Chassis> chunk;
        do {
            chunk = repository.findAll(ChassisSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<ChassisResponse> findByDeviceId(UUID deviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDeviceId(tenant(), deviceId, pageable).map(mapper::toResponse));
    }
}
