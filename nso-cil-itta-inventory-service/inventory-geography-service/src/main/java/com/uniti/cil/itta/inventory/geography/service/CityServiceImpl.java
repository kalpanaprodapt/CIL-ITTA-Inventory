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
import com.uniti.cil.itta.inventory.geography.event.CityEvents;
import com.uniti.cil.itta.inventory.geography.mapper.CityMapper;
import com.uniti.cil.itta.inventory.geography.model.City;
import com.uniti.cil.itta.inventory.geography.repository.CityRepository;
import com.uniti.cil.itta.inventory.geography.spec.CitySpecifications;
import com.uniti.cil.itta.inventory.geography.validation.CityValidator;
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
public class CityServiceImpl implements CityService {

    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CityRepository repository;
    private final CityMapper mapper;
    private final CityValidator validator;
    private final EventPublisher events;

    public CityServiceImpl(CityRepository repository, CityMapper mapper, CityValidator validator,
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

    private City load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("City", id));
    }

    @Override
    public CityResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CityResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("City", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CityResponse> search(CitySearchCriteria criteria, Pageable pageable) {
        Page<City> page = repository.findAll(CitySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CitySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CityResponse create(CityCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("City", "code", request.getCode());
        }
        City entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        City saved = repository.save(entity);
        CityResponse response = mapper.toResponse(saved);
        events.publish(CityEvents.created(response, actor()));
        log.info("Created City id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CityResponse update(UUID id, CityUpdateRequest request) {
        City entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "City " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("City", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        City saved = repository.save(entity);
        CityResponse response = mapper.toResponse(saved);
        events.publish(CityEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CityEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CityResponse changeStatus(UUID id, LifecycleStatus status) {
        City entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        City saved = repository.save(entity);
        CityResponse response = mapper.toResponse(saved);
        events.publish(CityEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        City entity = load(id);
        CityResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CityEvents.deleted(snapshot, actor()));
        log.info("Deleted City id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CityResponse> bulkCreate(List<CityCreateRequest> requests) {
        BulkResult<CityResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of City failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CityResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CityResponse> result = new BulkResult<>();
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
    public void exportCsv(CitySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CityMapper.CSV_HEADER));
        int page = 0;
        Page<City> chunk;
        do {
            chunk = repository.findAll(CitySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CityResponse> findByRegionId(UUID regionId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRegionId(tenant(), regionId, pageable).map(mapper::toResponse));
    }
}
