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
import com.uniti.cil.itta.inventory.topology.event.TopologyEdgeEvents;
import com.uniti.cil.itta.inventory.topology.mapper.TopologyEdgeMapper;
import com.uniti.cil.itta.inventory.topology.model.TopologyEdge;
import com.uniti.cil.itta.inventory.topology.repository.TopologyEdgeRepository;
import com.uniti.cil.itta.inventory.topology.spec.TopologyEdgeSpecifications;
import com.uniti.cil.itta.inventory.topology.validation.TopologyEdgeValidator;
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
public class TopologyEdgeServiceImpl implements TopologyEdgeService {

    private static final Logger log = LoggerFactory.getLogger(TopologyEdgeServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final TopologyEdgeRepository repository;
    private final TopologyEdgeMapper mapper;
    private final TopologyEdgeValidator validator;
    private final EventPublisher events;

    public TopologyEdgeServiceImpl(TopologyEdgeRepository repository, TopologyEdgeMapper mapper, TopologyEdgeValidator validator,
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

    private TopologyEdge load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("TopologyEdge", id));
    }

    @Override
    public TopologyEdgeResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public TopologyEdgeResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("TopologyEdge", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<TopologyEdgeResponse> search(TopologyEdgeSearchCriteria criteria, Pageable pageable) {
        Page<TopologyEdge> page = repository.findAll(TopologyEdgeSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<TopologyEdgeSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public TopologyEdgeResponse create(TopologyEdgeCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("TopologyEdge", "code", request.getCode());
        }
        TopologyEdge entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        TopologyEdge saved = repository.save(entity);
        TopologyEdgeResponse response = mapper.toResponse(saved);
        events.publish(TopologyEdgeEvents.created(response, actor()));
        log.info("Created TopologyEdge id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public TopologyEdgeResponse update(UUID id, TopologyEdgeUpdateRequest request) {
        TopologyEdge entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "TopologyEdge " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("TopologyEdge", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        TopologyEdge saved = repository.save(entity);
        TopologyEdgeResponse response = mapper.toResponse(saved);
        events.publish(TopologyEdgeEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(TopologyEdgeEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public TopologyEdgeResponse changeStatus(UUID id, LifecycleStatus status) {
        TopologyEdge entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        TopologyEdge saved = repository.save(entity);
        TopologyEdgeResponse response = mapper.toResponse(saved);
        events.publish(TopologyEdgeEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TopologyEdge entity = load(id);
        TopologyEdgeResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(TopologyEdgeEvents.deleted(snapshot, actor()));
        log.info("Deleted TopologyEdge id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<TopologyEdgeResponse> bulkCreate(List<TopologyEdgeCreateRequest> requests) {
        BulkResult<TopologyEdgeResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of TopologyEdge failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<TopologyEdgeResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<TopologyEdgeResponse> result = new BulkResult<>();
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
    public void exportCsv(TopologyEdgeSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(TopologyEdgeMapper.CSV_HEADER));
        int page = 0;
        Page<TopologyEdge> chunk;
        do {
            chunk = repository.findAll(TopologyEdgeSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<TopologyEdgeResponse> findByViewId(UUID viewId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndViewId(tenant(), viewId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<TopologyEdgeResponse> findBySourceNodeId(UUID sourceNodeId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSourceNodeId(tenant(), sourceNodeId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<TopologyEdgeResponse> findByTargetNodeId(UUID targetNodeId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndTargetNodeId(tenant(), targetNodeId, pageable).map(mapper::toResponse));
    }
}
