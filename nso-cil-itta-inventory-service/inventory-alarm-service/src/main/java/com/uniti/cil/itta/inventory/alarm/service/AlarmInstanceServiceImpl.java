package com.uniti.cil.itta.inventory.alarm.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.event.AlarmInstanceEvents;
import com.uniti.cil.itta.inventory.alarm.mapper.AlarmInstanceMapper;
import com.uniti.cil.itta.inventory.alarm.model.AlarmInstance;
import com.uniti.cil.itta.inventory.alarm.repository.AlarmInstanceRepository;
import com.uniti.cil.itta.inventory.alarm.spec.AlarmInstanceSpecifications;
import com.uniti.cil.itta.inventory.alarm.validation.AlarmInstanceValidator;
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
public class AlarmInstanceServiceImpl implements AlarmInstanceService {

    private static final Logger log = LoggerFactory.getLogger(AlarmInstanceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AlarmInstanceRepository repository;
    private final AlarmInstanceMapper mapper;
    private final AlarmInstanceValidator validator;
    private final EventPublisher events;

    public AlarmInstanceServiceImpl(AlarmInstanceRepository repository, AlarmInstanceMapper mapper, AlarmInstanceValidator validator,
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

    private AlarmInstance load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("AlarmInstance", id));
    }

    @Override
    public AlarmInstanceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AlarmInstanceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("AlarmInstance", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AlarmInstanceResponse> search(AlarmInstanceSearchCriteria criteria, Pageable pageable) {
        Page<AlarmInstance> page = repository.findAll(AlarmInstanceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AlarmInstanceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AlarmInstanceResponse create(AlarmInstanceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("AlarmInstance", "code", request.getCode());
        }
        AlarmInstance entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        AlarmInstance saved = repository.save(entity);
        AlarmInstanceResponse response = mapper.toResponse(saved);
        events.publish(AlarmInstanceEvents.created(response, actor()));
        log.info("Created AlarmInstance id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AlarmInstanceResponse update(UUID id, AlarmInstanceUpdateRequest request) {
        AlarmInstance entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "AlarmInstance " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("AlarmInstance", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        AlarmInstance saved = repository.save(entity);
        AlarmInstanceResponse response = mapper.toResponse(saved);
        events.publish(AlarmInstanceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AlarmInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AlarmInstanceResponse changeStatus(UUID id, LifecycleStatus status) {
        AlarmInstance entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        AlarmInstance saved = repository.save(entity);
        AlarmInstanceResponse response = mapper.toResponse(saved);
        events.publish(AlarmInstanceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AlarmInstance entity = load(id);
        AlarmInstanceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AlarmInstanceEvents.deleted(snapshot, actor()));
        log.info("Deleted AlarmInstance id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AlarmInstanceResponse> bulkCreate(List<AlarmInstanceCreateRequest> requests) {
        BulkResult<AlarmInstanceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of AlarmInstance failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AlarmInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AlarmInstanceResponse> result = new BulkResult<>();
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
    public void exportCsv(AlarmInstanceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AlarmInstanceMapper.CSV_HEADER));
        int page = 0;
        Page<AlarmInstance> chunk;
        do {
            chunk = repository.findAll(AlarmInstanceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<AlarmInstanceResponse> findByDefinitionId(UUID definitionId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDefinitionId(tenant(), definitionId, pageable).map(mapper::toResponse));
    }
}
