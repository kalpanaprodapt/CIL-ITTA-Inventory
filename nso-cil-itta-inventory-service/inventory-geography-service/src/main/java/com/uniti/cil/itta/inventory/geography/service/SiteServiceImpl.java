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
import com.uniti.cil.itta.inventory.geography.event.SiteEvents;
import com.uniti.cil.itta.inventory.geography.mapper.SiteMapper;
import com.uniti.cil.itta.inventory.geography.model.Site;
import com.uniti.cil.itta.inventory.geography.repository.SiteRepository;
import com.uniti.cil.itta.inventory.geography.spec.SiteSpecifications;
import com.uniti.cil.itta.inventory.geography.validation.SiteValidator;
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
public class SiteServiceImpl implements SiteService {

    private static final Logger log = LoggerFactory.getLogger(SiteServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SiteRepository repository;
    private final SiteMapper mapper;
    private final SiteValidator validator;
    private final EventPublisher events;

    public SiteServiceImpl(SiteRepository repository, SiteMapper mapper, SiteValidator validator,
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

    private Site load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Site", id));
    }

    @Override
    public SiteResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SiteResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Site", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SiteResponse> search(SiteSearchCriteria criteria, Pageable pageable) {
        Page<Site> page = repository.findAll(SiteSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SiteSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SiteResponse create(SiteCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Site", "code", request.getCode());
        }
        Site entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Site saved = repository.save(entity);
        SiteResponse response = mapper.toResponse(saved);
        events.publish(SiteEvents.created(response, actor()));
        log.info("Created Site id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SiteResponse update(UUID id, SiteUpdateRequest request) {
        Site entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Site " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Site", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Site saved = repository.save(entity);
        SiteResponse response = mapper.toResponse(saved);
        events.publish(SiteEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SiteEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SiteResponse changeStatus(UUID id, LifecycleStatus status) {
        Site entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Site saved = repository.save(entity);
        SiteResponse response = mapper.toResponse(saved);
        events.publish(SiteEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Site entity = load(id);
        SiteResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SiteEvents.deleted(snapshot, actor()));
        log.info("Deleted Site id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SiteResponse> bulkCreate(List<SiteCreateRequest> requests) {
        BulkResult<SiteResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Site failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SiteResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SiteResponse> result = new BulkResult<>();
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
    public void exportCsv(SiteSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SiteMapper.CSV_HEADER));
        int page = 0;
        Page<Site> chunk;
        do {
            chunk = repository.findAll(SiteSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SiteResponse> findByCityId(UUID cityId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCityId(tenant(), cityId, pageable).map(mapper::toResponse));
    }
}
