package com.uniti.cil.itta.inventory.vendor.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.event.SkuEvents;
import com.uniti.cil.itta.inventory.vendor.mapper.SkuMapper;
import com.uniti.cil.itta.inventory.vendor.model.Sku;
import com.uniti.cil.itta.inventory.vendor.repository.SkuRepository;
import com.uniti.cil.itta.inventory.vendor.spec.SkuSpecifications;
import com.uniti.cil.itta.inventory.vendor.validation.SkuValidator;
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
public class SkuServiceImpl implements SkuService {

    private static final Logger log = LoggerFactory.getLogger(SkuServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SkuRepository repository;
    private final SkuMapper mapper;
    private final SkuValidator validator;
    private final EventPublisher events;

    public SkuServiceImpl(SkuRepository repository, SkuMapper mapper, SkuValidator validator,
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

    private Sku load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Sku", id));
    }

    @Override
    public SkuResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SkuResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Sku", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SkuResponse> search(SkuSearchCriteria criteria, Pageable pageable) {
        Page<Sku> page = repository.findAll(SkuSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SkuSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SkuResponse create(SkuCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Sku", "code", request.getCode());
        }
        Sku entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Sku saved = repository.save(entity);
        SkuResponse response = mapper.toResponse(saved);
        events.publish(SkuEvents.created(response, actor()));
        log.info("Created Sku id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SkuResponse update(UUID id, SkuUpdateRequest request) {
        Sku entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Sku " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Sku", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Sku saved = repository.save(entity);
        SkuResponse response = mapper.toResponse(saved);
        events.publish(SkuEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SkuEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SkuResponse changeStatus(UUID id, LifecycleStatus status) {
        Sku entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Sku saved = repository.save(entity);
        SkuResponse response = mapper.toResponse(saved);
        events.publish(SkuEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Sku entity = load(id);
        SkuResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SkuEvents.deleted(snapshot, actor()));
        log.info("Deleted Sku id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SkuResponse> bulkCreate(List<SkuCreateRequest> requests) {
        BulkResult<SkuResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Sku failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SkuResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SkuResponse> result = new BulkResult<>();
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
    public void exportCsv(SkuSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SkuMapper.CSV_HEADER));
        int page = 0;
        Page<Sku> chunk;
        do {
            chunk = repository.findAll(SkuSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SkuResponse> findByModelId(UUID modelId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndModelId(tenant(), modelId, pageable).map(mapper::toResponse));
    }
}
