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
import com.uniti.cil.itta.inventory.cabling.event.PatchPanelEvents;
import com.uniti.cil.itta.inventory.cabling.mapper.PatchPanelMapper;
import com.uniti.cil.itta.inventory.cabling.model.PatchPanel;
import com.uniti.cil.itta.inventory.cabling.repository.PatchPanelRepository;
import com.uniti.cil.itta.inventory.cabling.spec.PatchPanelSpecifications;
import com.uniti.cil.itta.inventory.cabling.validation.PatchPanelValidator;
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
public class PatchPanelServiceImpl implements PatchPanelService {

    private static final Logger log = LoggerFactory.getLogger(PatchPanelServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PatchPanelRepository repository;
    private final PatchPanelMapper mapper;
    private final PatchPanelValidator validator;
    private final EventPublisher events;

    public PatchPanelServiceImpl(PatchPanelRepository repository, PatchPanelMapper mapper, PatchPanelValidator validator,
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

    private PatchPanel load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PatchPanel", id));
    }

    @Override
    public PatchPanelResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PatchPanelResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PatchPanel", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PatchPanelResponse> search(PatchPanelSearchCriteria criteria, Pageable pageable) {
        Page<PatchPanel> page = repository.findAll(PatchPanelSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PatchPanelSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PatchPanelResponse create(PatchPanelCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PatchPanel", "code", request.getCode());
        }
        PatchPanel entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PatchPanel saved = repository.save(entity);
        PatchPanelResponse response = mapper.toResponse(saved);
        events.publish(PatchPanelEvents.created(response, actor()));
        log.info("Created PatchPanel id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PatchPanelResponse update(UUID id, PatchPanelUpdateRequest request) {
        PatchPanel entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PatchPanel " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PatchPanel", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PatchPanel saved = repository.save(entity);
        PatchPanelResponse response = mapper.toResponse(saved);
        events.publish(PatchPanelEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PatchPanelEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PatchPanelResponse changeStatus(UUID id, LifecycleStatus status) {
        PatchPanel entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PatchPanel saved = repository.save(entity);
        PatchPanelResponse response = mapper.toResponse(saved);
        events.publish(PatchPanelEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PatchPanel entity = load(id);
        PatchPanelResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PatchPanelEvents.deleted(snapshot, actor()));
        log.info("Deleted PatchPanel id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PatchPanelResponse> bulkCreate(List<PatchPanelCreateRequest> requests) {
        BulkResult<PatchPanelResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PatchPanel failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PatchPanelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PatchPanelResponse> result = new BulkResult<>();
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
    public void exportCsv(PatchPanelSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PatchPanelMapper.CSV_HEADER));
        int page = 0;
        Page<PatchPanel> chunk;
        do {
            chunk = repository.findAll(PatchPanelSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PatchPanelResponse> findByRoomId(UUID roomId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRoomId(tenant(), roomId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<PatchPanelResponse> findByRackId(UUID rackId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRackId(tenant(), rackId, pageable).map(mapper::toResponse));
    }
}
