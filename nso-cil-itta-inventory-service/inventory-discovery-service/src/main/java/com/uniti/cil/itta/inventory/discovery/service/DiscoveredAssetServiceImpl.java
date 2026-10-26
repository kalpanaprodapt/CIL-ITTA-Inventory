package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.event.DiscoveredAssetEvents;
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveredAssetMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveredAsset;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveredAssetRepository;
import com.uniti.cil.itta.inventory.discovery.spec.DiscoveredAssetSpecifications;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveredAssetValidator;
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
public class DiscoveredAssetServiceImpl implements DiscoveredAssetService {

    private static final Logger log = LoggerFactory.getLogger(DiscoveredAssetServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final DiscoveredAssetRepository repository;
    private final DiscoveredAssetMapper mapper;
    private final DiscoveredAssetValidator validator;
    private final EventPublisher events;

    public DiscoveredAssetServiceImpl(DiscoveredAssetRepository repository, DiscoveredAssetMapper mapper, DiscoveredAssetValidator validator,
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

    private DiscoveredAsset load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveredAsset", id));
    }

    @Override
    public DiscoveredAssetResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public DiscoveredAssetResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveredAsset", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<DiscoveredAssetResponse> search(DiscoveredAssetSearchCriteria criteria, Pageable pageable) {
        Page<DiscoveredAsset> page = repository.findAll(DiscoveredAssetSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<DiscoveredAssetSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public DiscoveredAssetResponse create(DiscoveredAssetCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("DiscoveredAsset", "code", request.getCode());
        }
        DiscoveredAsset entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        DiscoveredAsset saved = repository.save(entity);
        DiscoveredAssetResponse response = mapper.toResponse(saved);
        events.publish(DiscoveredAssetEvents.created(response, actor()));
        log.info("Created DiscoveredAsset id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public DiscoveredAssetResponse update(UUID id, DiscoveredAssetUpdateRequest request) {
        DiscoveredAsset entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "DiscoveredAsset " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("DiscoveredAsset", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        DiscoveredAsset saved = repository.save(entity);
        DiscoveredAssetResponse response = mapper.toResponse(saved);
        events.publish(DiscoveredAssetEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(DiscoveredAssetEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public DiscoveredAssetResponse changeStatus(UUID id, LifecycleStatus status) {
        DiscoveredAsset entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        DiscoveredAsset saved = repository.save(entity);
        DiscoveredAssetResponse response = mapper.toResponse(saved);
        events.publish(DiscoveredAssetEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DiscoveredAsset entity = load(id);
        DiscoveredAssetResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(DiscoveredAssetEvents.deleted(snapshot, actor()));
        log.info("Deleted DiscoveredAsset id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<DiscoveredAssetResponse> bulkCreate(List<DiscoveredAssetCreateRequest> requests) {
        BulkResult<DiscoveredAssetResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of DiscoveredAsset failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<DiscoveredAssetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<DiscoveredAssetResponse> result = new BulkResult<>();
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
    public void exportCsv(DiscoveredAssetSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(DiscoveredAssetMapper.CSV_HEADER));
        int page = 0;
        Page<DiscoveredAsset> chunk;
        do {
            chunk = repository.findAll(DiscoveredAssetSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<DiscoveredAssetResponse> findByJobId(UUID jobId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndJobId(tenant(), jobId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<DiscoveredAssetResponse> findByMatchedAssetId(UUID matchedAssetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndMatchedAssetId(tenant(), matchedAssetId, pageable).map(mapper::toResponse));
    }
}
