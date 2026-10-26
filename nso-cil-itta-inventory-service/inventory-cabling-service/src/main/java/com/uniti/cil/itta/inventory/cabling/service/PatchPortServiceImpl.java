package com.uniti.cil.itta.inventory.cabling.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.event.PatchPortEvents;
import com.uniti.cil.itta.inventory.cabling.mapper.PatchPortMapper;
import com.uniti.cil.itta.inventory.cabling.model.PatchPort;
import com.uniti.cil.itta.inventory.cabling.repository.PatchPortRepository;
import com.uniti.cil.itta.inventory.cabling.spec.PatchPortSpecifications;
import com.uniti.cil.itta.inventory.cabling.validation.PatchPortValidator;
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
public class PatchPortServiceImpl implements PatchPortService {

    private static final Logger log = LoggerFactory.getLogger(PatchPortServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PatchPortRepository repository;
    private final PatchPortMapper mapper;
    private final PatchPortValidator validator;
    private final EventPublisher events;

    public PatchPortServiceImpl(PatchPortRepository repository, PatchPortMapper mapper, PatchPortValidator validator,
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

    private PatchPort load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PatchPort", id));
    }

    @Override
    public PatchPortResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PatchPortResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PatchPort", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PatchPortResponse> search(PatchPortSearchCriteria criteria, Pageable pageable) {
        Page<PatchPort> page = repository.findAll(PatchPortSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PatchPortSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PatchPortResponse create(PatchPortCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PatchPort", "code", request.getCode());
        }
        PatchPort entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PatchPort saved = repository.save(entity);
        PatchPortResponse response = mapper.toResponse(saved);
        events.publish(PatchPortEvents.created(response, actor()));
        log.info("Created PatchPort id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PatchPortResponse update(UUID id, PatchPortUpdateRequest request) {
        PatchPort entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PatchPort " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PatchPort", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PatchPort saved = repository.save(entity);
        PatchPortResponse response = mapper.toResponse(saved);
        events.publish(PatchPortEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PatchPortEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PatchPortResponse changeStatus(UUID id, LifecycleStatus status) {
        PatchPort entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PatchPort saved = repository.save(entity);
        PatchPortResponse response = mapper.toResponse(saved);
        events.publish(PatchPortEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PatchPort entity = load(id);
        PatchPortResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PatchPortEvents.deleted(snapshot, actor()));
        log.info("Deleted PatchPort id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PatchPortResponse> bulkCreate(List<PatchPortCreateRequest> requests) {
        BulkResult<PatchPortResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PatchPort failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PatchPortResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PatchPortResponse> result = new BulkResult<>();
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
    public void exportCsv(PatchPortSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PatchPortMapper.CSV_HEADER));
        int page = 0;
        Page<PatchPort> chunk;
        do {
            chunk = repository.findAll(PatchPortSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PatchPortResponse> findByPanelId(UUID panelId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPanelId(tenant(), panelId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<PatchPortResponse> findByConnectedCableId(UUID connectedCableId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndConnectedCableId(tenant(), connectedCableId, pageable).map(mapper::toResponse));
    }
}
