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
import com.uniti.cil.itta.inventory.alarm.event.AlarmDefinitionEvents;
import com.uniti.cil.itta.inventory.alarm.mapper.AlarmDefinitionMapper;
import com.uniti.cil.itta.inventory.alarm.model.AlarmDefinition;
import com.uniti.cil.itta.inventory.alarm.repository.AlarmDefinitionRepository;
import com.uniti.cil.itta.inventory.alarm.spec.AlarmDefinitionSpecifications;
import com.uniti.cil.itta.inventory.alarm.validation.AlarmDefinitionValidator;
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
public class AlarmDefinitionServiceImpl implements AlarmDefinitionService {

    private static final Logger log = LoggerFactory.getLogger(AlarmDefinitionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AlarmDefinitionRepository repository;
    private final AlarmDefinitionMapper mapper;
    private final AlarmDefinitionValidator validator;
    private final EventPublisher events;

    public AlarmDefinitionServiceImpl(AlarmDefinitionRepository repository, AlarmDefinitionMapper mapper, AlarmDefinitionValidator validator,
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

    private AlarmDefinition load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("AlarmDefinition", id));
    }

    @Override
    public AlarmDefinitionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AlarmDefinitionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("AlarmDefinition", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AlarmDefinitionResponse> search(AlarmDefinitionSearchCriteria criteria, Pageable pageable) {
        Page<AlarmDefinition> page = repository.findAll(AlarmDefinitionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AlarmDefinitionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AlarmDefinitionResponse create(AlarmDefinitionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("AlarmDefinition", "code", request.getCode());
        }
        AlarmDefinition entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        AlarmDefinition saved = repository.save(entity);
        AlarmDefinitionResponse response = mapper.toResponse(saved);
        events.publish(AlarmDefinitionEvents.created(response, actor()));
        log.info("Created AlarmDefinition id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AlarmDefinitionResponse update(UUID id, AlarmDefinitionUpdateRequest request) {
        AlarmDefinition entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "AlarmDefinition " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("AlarmDefinition", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        AlarmDefinition saved = repository.save(entity);
        AlarmDefinitionResponse response = mapper.toResponse(saved);
        events.publish(AlarmDefinitionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AlarmDefinitionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AlarmDefinitionResponse changeStatus(UUID id, LifecycleStatus status) {
        AlarmDefinition entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        AlarmDefinition saved = repository.save(entity);
        AlarmDefinitionResponse response = mapper.toResponse(saved);
        events.publish(AlarmDefinitionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AlarmDefinition entity = load(id);
        AlarmDefinitionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AlarmDefinitionEvents.deleted(snapshot, actor()));
        log.info("Deleted AlarmDefinition id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AlarmDefinitionResponse> bulkCreate(List<AlarmDefinitionCreateRequest> requests) {
        BulkResult<AlarmDefinitionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of AlarmDefinition failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AlarmDefinitionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AlarmDefinitionResponse> result = new BulkResult<>();
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
    public void exportCsv(AlarmDefinitionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AlarmDefinitionMapper.CSV_HEADER));
        int page = 0;
        Page<AlarmDefinition> chunk;
        do {
            chunk = repository.findAll(AlarmDefinitionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
