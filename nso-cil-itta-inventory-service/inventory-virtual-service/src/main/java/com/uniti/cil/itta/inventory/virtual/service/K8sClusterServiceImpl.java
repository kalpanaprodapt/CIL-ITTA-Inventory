package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.event.K8sClusterEvents;
import com.uniti.cil.itta.inventory.virtual.mapper.K8sClusterMapper;
import com.uniti.cil.itta.inventory.virtual.model.K8sCluster;
import com.uniti.cil.itta.inventory.virtual.repository.K8sClusterRepository;
import com.uniti.cil.itta.inventory.virtual.spec.K8sClusterSpecifications;
import com.uniti.cil.itta.inventory.virtual.validation.K8sClusterValidator;
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
public class K8sClusterServiceImpl implements K8sClusterService {

    private static final Logger log = LoggerFactory.getLogger(K8sClusterServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final K8sClusterRepository repository;
    private final K8sClusterMapper mapper;
    private final K8sClusterValidator validator;
    private final EventPublisher events;

    public K8sClusterServiceImpl(K8sClusterRepository repository, K8sClusterMapper mapper, K8sClusterValidator validator,
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

    private K8sCluster load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("K8sCluster", id));
    }

    @Override
    public K8sClusterResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public K8sClusterResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("K8sCluster", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<K8sClusterResponse> search(K8sClusterSearchCriteria criteria, Pageable pageable) {
        Page<K8sCluster> page = repository.findAll(K8sClusterSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<K8sClusterSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public K8sClusterResponse create(K8sClusterCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("K8sCluster", "code", request.getCode());
        }
        K8sCluster entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        K8sCluster saved = repository.save(entity);
        K8sClusterResponse response = mapper.toResponse(saved);
        events.publish(K8sClusterEvents.created(response, actor()));
        log.info("Created K8sCluster id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public K8sClusterResponse update(UUID id, K8sClusterUpdateRequest request) {
        K8sCluster entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "K8sCluster " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("K8sCluster", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        K8sCluster saved = repository.save(entity);
        K8sClusterResponse response = mapper.toResponse(saved);
        events.publish(K8sClusterEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(K8sClusterEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public K8sClusterResponse changeStatus(UUID id, LifecycleStatus status) {
        K8sCluster entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        K8sCluster saved = repository.save(entity);
        K8sClusterResponse response = mapper.toResponse(saved);
        events.publish(K8sClusterEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        K8sCluster entity = load(id);
        K8sClusterResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(K8sClusterEvents.deleted(snapshot, actor()));
        log.info("Deleted K8sCluster id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<K8sClusterResponse> bulkCreate(List<K8sClusterCreateRequest> requests) {
        BulkResult<K8sClusterResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of K8sCluster failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<K8sClusterResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<K8sClusterResponse> result = new BulkResult<>();
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
    public void exportCsv(K8sClusterSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(K8sClusterMapper.CSV_HEADER));
        int page = 0;
        Page<K8sCluster> chunk;
        do {
            chunk = repository.findAll(K8sClusterSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<K8sClusterResponse> findByCloudAccountId(UUID cloudAccountId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCloudAccountId(tenant(), cloudAccountId, pageable).map(mapper::toResponse));
    }
}
