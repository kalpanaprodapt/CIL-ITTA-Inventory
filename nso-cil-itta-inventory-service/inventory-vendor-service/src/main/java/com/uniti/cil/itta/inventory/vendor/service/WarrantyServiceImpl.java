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
import com.uniti.cil.itta.inventory.vendor.event.WarrantyEvents;
import com.uniti.cil.itta.inventory.vendor.mapper.WarrantyMapper;
import com.uniti.cil.itta.inventory.vendor.model.Warranty;
import com.uniti.cil.itta.inventory.vendor.repository.WarrantyRepository;
import com.uniti.cil.itta.inventory.vendor.spec.WarrantySpecifications;
import com.uniti.cil.itta.inventory.vendor.validation.WarrantyValidator;
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
public class WarrantyServiceImpl implements WarrantyService {

    private static final Logger log = LoggerFactory.getLogger(WarrantyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final WarrantyRepository repository;
    private final WarrantyMapper mapper;
    private final WarrantyValidator validator;
    private final EventPublisher events;

    public WarrantyServiceImpl(WarrantyRepository repository, WarrantyMapper mapper, WarrantyValidator validator,
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

    private Warranty load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Warranty", id));
    }

    @Override
    public WarrantyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public WarrantyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Warranty", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<WarrantyResponse> search(WarrantySearchCriteria criteria, Pageable pageable) {
        Page<Warranty> page = repository.findAll(WarrantySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<WarrantySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public WarrantyResponse create(WarrantyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Warranty", "code", request.getCode());
        }
        Warranty entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Warranty saved = repository.save(entity);
        WarrantyResponse response = mapper.toResponse(saved);
        events.publish(WarrantyEvents.created(response, actor()));
        log.info("Created Warranty id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public WarrantyResponse update(UUID id, WarrantyUpdateRequest request) {
        Warranty entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Warranty " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Warranty", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Warranty saved = repository.save(entity);
        WarrantyResponse response = mapper.toResponse(saved);
        events.publish(WarrantyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(WarrantyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public WarrantyResponse changeStatus(UUID id, LifecycleStatus status) {
        Warranty entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Warranty saved = repository.save(entity);
        WarrantyResponse response = mapper.toResponse(saved);
        events.publish(WarrantyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Warranty entity = load(id);
        WarrantyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(WarrantyEvents.deleted(snapshot, actor()));
        log.info("Deleted Warranty id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<WarrantyResponse> bulkCreate(List<WarrantyCreateRequest> requests) {
        BulkResult<WarrantyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Warranty failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<WarrantyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<WarrantyResponse> result = new BulkResult<>();
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
    public void exportCsv(WarrantySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(WarrantyMapper.CSV_HEADER));
        int page = 0;
        Page<Warranty> chunk;
        do {
            chunk = repository.findAll(WarrantySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<WarrantyResponse> findByAssetId(UUID assetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssetId(tenant(), assetId, pageable).map(mapper::toResponse));
    }
}
