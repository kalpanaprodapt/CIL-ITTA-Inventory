package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.event.PortEvents;
import com.uniti.cil.itta.inventory.port.mapper.PortMapper;
import com.uniti.cil.itta.inventory.port.model.Port;
import com.uniti.cil.itta.inventory.port.repository.PortRepository;
import com.uniti.cil.itta.inventory.port.spec.PortSpecifications;
import com.uniti.cil.itta.inventory.port.validation.PortValidator;
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
public class PortServiceImpl implements PortService {

    private static final Logger log = LoggerFactory.getLogger(PortServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PortRepository repository;
    private final PortMapper mapper;
    private final PortValidator validator;
    private final EventPublisher events;

    public PortServiceImpl(PortRepository repository, PortMapper mapper, PortValidator validator,
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

    private Port load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Port", id));
    }

    @Override
    public PortResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PortResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Port", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PortResponse> search(PortSearchCriteria criteria, Pageable pageable) {
        Page<Port> page = repository.findAll(PortSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PortSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PortResponse create(PortCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Port", "code", request.getCode());
        }
        Port entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Port saved = repository.save(entity);
        PortResponse response = mapper.toResponse(saved);
        events.publish(PortEvents.created(response, actor()));
        log.info("Created Port id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PortResponse update(UUID id, PortUpdateRequest request) {
        Port entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Port " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Port", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Port saved = repository.save(entity);
        PortResponse response = mapper.toResponse(saved);
        events.publish(PortEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PortEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PortResponse changeStatus(UUID id, LifecycleStatus status) {
        Port entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Port saved = repository.save(entity);
        PortResponse response = mapper.toResponse(saved);
        events.publish(PortEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Port entity = load(id);
        PortResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PortEvents.deleted(snapshot, actor()));
        log.info("Deleted Port id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PortResponse> bulkCreate(List<PortCreateRequest> requests) {
        BulkResult<PortResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Port failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PortResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PortResponse> result = new BulkResult<>();
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
    public void exportCsv(PortSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PortMapper.CSV_HEADER));
        int page = 0;
        Page<Port> chunk;
        do {
            chunk = repository.findAll(PortSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PortResponse> findByDeviceId(UUID deviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndDeviceId(tenant(), deviceId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<PortResponse> findByCardId(UUID cardId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCardId(tenant(), cardId, pageable).map(mapper::toResponse));
    }
}
