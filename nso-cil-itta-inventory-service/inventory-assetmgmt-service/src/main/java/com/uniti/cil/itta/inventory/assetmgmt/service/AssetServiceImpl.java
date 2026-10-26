package com.uniti.cil.itta.inventory.assetmgmt.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.event.AssetEvents;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.AssetMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.Asset;
import com.uniti.cil.itta.inventory.assetmgmt.repository.AssetRepository;
import com.uniti.cil.itta.inventory.assetmgmt.spec.AssetSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.validation.AssetValidator;
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
public class AssetServiceImpl implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AssetRepository repository;
    private final AssetMapper mapper;
    private final AssetValidator validator;
    private final EventPublisher events;

    public AssetServiceImpl(AssetRepository repository, AssetMapper mapper, AssetValidator validator,
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

    private Asset load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", id));
    }

    @Override
    public AssetResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AssetResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AssetResponse> search(AssetSearchCriteria criteria, Pageable pageable) {
        Page<Asset> page = repository.findAll(AssetSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AssetSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AssetResponse create(AssetCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Asset", "code", request.getCode());
        }
        Asset entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Asset saved = repository.save(entity);
        AssetResponse response = mapper.toResponse(saved);
        events.publish(AssetEvents.created(response, actor()));
        log.info("Created Asset id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AssetResponse update(UUID id, AssetUpdateRequest request) {
        Asset entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Asset " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Asset", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Asset saved = repository.save(entity);
        AssetResponse response = mapper.toResponse(saved);
        events.publish(AssetEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AssetEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AssetResponse changeStatus(UUID id, LifecycleStatus status) {
        Asset entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Asset saved = repository.save(entity);
        AssetResponse response = mapper.toResponse(saved);
        events.publish(AssetEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Asset entity = load(id);
        AssetResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AssetEvents.deleted(snapshot, actor()));
        log.info("Deleted Asset id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AssetResponse> bulkCreate(List<AssetCreateRequest> requests) {
        BulkResult<AssetResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Asset failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AssetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AssetResponse> result = new BulkResult<>();
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
    public void exportCsv(AssetSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AssetMapper.CSV_HEADER));
        int page = 0;
        Page<Asset> chunk;
        do {
            chunk = repository.findAll(AssetSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
