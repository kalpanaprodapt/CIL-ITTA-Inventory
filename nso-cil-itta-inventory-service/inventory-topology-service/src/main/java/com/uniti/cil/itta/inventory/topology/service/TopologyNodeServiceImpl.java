package com.uniti.cil.itta.inventory.topology.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.event.TopologyNodeEvents;
import com.uniti.cil.itta.inventory.topology.mapper.TopologyNodeMapper;
import com.uniti.cil.itta.inventory.topology.model.TopologyNode;
import com.uniti.cil.itta.inventory.topology.repository.TopologyNodeRepository;
import com.uniti.cil.itta.inventory.topology.spec.TopologyNodeSpecifications;
import com.uniti.cil.itta.inventory.topology.validation.TopologyNodeValidator;
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
public class TopologyNodeServiceImpl implements TopologyNodeService {

    private static final Logger log = LoggerFactory.getLogger(TopologyNodeServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final TopologyNodeRepository repository;
    private final TopologyNodeMapper mapper;
    private final TopologyNodeValidator validator;
    private final EventPublisher events;

    public TopologyNodeServiceImpl(TopologyNodeRepository repository, TopologyNodeMapper mapper, TopologyNodeValidator validator,
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

    private TopologyNode load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("TopologyNode", id));
    }

    @Override
    public TopologyNodeResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public TopologyNodeResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("TopologyNode", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<TopologyNodeResponse> search(TopologyNodeSearchCriteria criteria, Pageable pageable) {
        Page<TopologyNode> page = repository.findAll(TopologyNodeSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<TopologyNodeSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public TopologyNodeResponse create(TopologyNodeCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("TopologyNode", "code", request.getCode());
        }
        TopologyNode entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        TopologyNode saved = repository.save(entity);
        TopologyNodeResponse response = mapper.toResponse(saved);
        events.publish(TopologyNodeEvents.created(response, actor()));
        log.info("Created TopologyNode id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public TopologyNodeResponse update(UUID id, TopologyNodeUpdateRequest request) {
        TopologyNode entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "TopologyNode " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("TopologyNode", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        TopologyNode saved = repository.save(entity);
        TopologyNodeResponse response = mapper.toResponse(saved);
        events.publish(TopologyNodeEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(TopologyNodeEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public TopologyNodeResponse changeStatus(UUID id, LifecycleStatus status) {
        TopologyNode entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        TopologyNode saved = repository.save(entity);
        TopologyNodeResponse response = mapper.toResponse(saved);
        events.publish(TopologyNodeEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TopologyNode entity = load(id);
        TopologyNodeResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(TopologyNodeEvents.deleted(snapshot, actor()));
        log.info("Deleted TopologyNode id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<TopologyNodeResponse> bulkCreate(List<TopologyNodeCreateRequest> requests) {
        BulkResult<TopologyNodeResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of TopologyNode failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<TopologyNodeResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<TopologyNodeResponse> result = new BulkResult<>();
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
    public void exportCsv(TopologyNodeSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(TopologyNodeMapper.CSV_HEADER));
        int page = 0;
        Page<TopologyNode> chunk;
        do {
            chunk = repository.findAll(TopologyNodeSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<TopologyNodeResponse> findByViewId(UUID viewId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndViewId(tenant(), viewId, pageable).map(mapper::toResponse));
    }
}
