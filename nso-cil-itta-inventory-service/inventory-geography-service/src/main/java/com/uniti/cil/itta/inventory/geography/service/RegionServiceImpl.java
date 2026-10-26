package com.uniti.cil.itta.inventory.geography.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.event.RegionEvents;
import com.uniti.cil.itta.inventory.geography.mapper.RegionMapper;
import com.uniti.cil.itta.inventory.geography.model.Region;
import com.uniti.cil.itta.inventory.geography.repository.RegionRepository;
import com.uniti.cil.itta.inventory.geography.spec.RegionSpecifications;
import com.uniti.cil.itta.inventory.geography.validation.RegionValidator;
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
public class RegionServiceImpl implements RegionService {

    private static final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final RegionRepository repository;
    private final RegionMapper mapper;
    private final RegionValidator validator;
    private final EventPublisher events;

    public RegionServiceImpl(RegionRepository repository, RegionMapper mapper, RegionValidator validator,
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

    private Region load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Region", id));
    }

    @Override
    public RegionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public RegionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Region", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<RegionResponse> search(RegionSearchCriteria criteria, Pageable pageable) {
        Page<Region> page = repository.findAll(RegionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<RegionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public RegionResponse create(RegionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Region", "code", request.getCode());
        }
        Region entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Region saved = repository.save(entity);
        RegionResponse response = mapper.toResponse(saved);
        events.publish(RegionEvents.created(response, actor()));
        log.info("Created Region id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public RegionResponse update(UUID id, RegionUpdateRequest request) {
        Region entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Region " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Region", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Region saved = repository.save(entity);
        RegionResponse response = mapper.toResponse(saved);
        events.publish(RegionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(RegionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public RegionResponse changeStatus(UUID id, LifecycleStatus status) {
        Region entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Region saved = repository.save(entity);
        RegionResponse response = mapper.toResponse(saved);
        events.publish(RegionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Region entity = load(id);
        RegionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(RegionEvents.deleted(snapshot, actor()));
        log.info("Deleted Region id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<RegionResponse> bulkCreate(List<RegionCreateRequest> requests) {
        BulkResult<RegionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Region failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<RegionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<RegionResponse> result = new BulkResult<>();
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
    public void exportCsv(RegionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(RegionMapper.CSV_HEADER));
        int page = 0;
        Page<Region> chunk;
        do {
            chunk = repository.findAll(RegionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<RegionResponse> findByCountryId(UUID countryId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCountryId(tenant(), countryId, pageable).map(mapper::toResponse));
    }
}
