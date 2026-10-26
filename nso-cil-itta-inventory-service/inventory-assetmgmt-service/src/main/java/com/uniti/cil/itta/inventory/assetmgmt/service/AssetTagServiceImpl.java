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
import com.uniti.cil.itta.inventory.assetmgmt.event.AssetTagEvents;
import com.uniti.cil.itta.inventory.assetmgmt.mapper.AssetTagMapper;
import com.uniti.cil.itta.inventory.assetmgmt.model.AssetTag;
import com.uniti.cil.itta.inventory.assetmgmt.repository.AssetTagRepository;
import com.uniti.cil.itta.inventory.assetmgmt.spec.AssetTagSpecifications;
import com.uniti.cil.itta.inventory.assetmgmt.validation.AssetTagValidator;
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
public class AssetTagServiceImpl implements AssetTagService {

    private static final Logger log = LoggerFactory.getLogger(AssetTagServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AssetTagRepository repository;
    private final AssetTagMapper mapper;
    private final AssetTagValidator validator;
    private final EventPublisher events;

    public AssetTagServiceImpl(AssetTagRepository repository, AssetTagMapper mapper, AssetTagValidator validator,
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

    private AssetTag load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("AssetTag", id));
    }

    @Override
    public AssetTagResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AssetTagResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("AssetTag", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AssetTagResponse> search(AssetTagSearchCriteria criteria, Pageable pageable) {
        Page<AssetTag> page = repository.findAll(AssetTagSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AssetTagSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AssetTagResponse create(AssetTagCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("AssetTag", "code", request.getCode());
        }
        AssetTag entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        AssetTag saved = repository.save(entity);
        AssetTagResponse response = mapper.toResponse(saved);
        events.publish(AssetTagEvents.created(response, actor()));
        log.info("Created AssetTag id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AssetTagResponse update(UUID id, AssetTagUpdateRequest request) {
        AssetTag entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "AssetTag " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("AssetTag", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        AssetTag saved = repository.save(entity);
        AssetTagResponse response = mapper.toResponse(saved);
        events.publish(AssetTagEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AssetTagEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AssetTagResponse changeStatus(UUID id, LifecycleStatus status) {
        AssetTag entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        AssetTag saved = repository.save(entity);
        AssetTagResponse response = mapper.toResponse(saved);
        events.publish(AssetTagEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AssetTag entity = load(id);
        AssetTagResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AssetTagEvents.deleted(snapshot, actor()));
        log.info("Deleted AssetTag id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AssetTagResponse> bulkCreate(List<AssetTagCreateRequest> requests) {
        BulkResult<AssetTagResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of AssetTag failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AssetTagResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AssetTagResponse> result = new BulkResult<>();
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
    public void exportCsv(AssetTagSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AssetTagMapper.CSV_HEADER));
        int page = 0;
        Page<AssetTag> chunk;
        do {
            chunk = repository.findAll(AssetTagSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<AssetTagResponse> findByAssetId(UUID assetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssetId(tenant(), assetId, pageable).map(mapper::toResponse));
    }
}
