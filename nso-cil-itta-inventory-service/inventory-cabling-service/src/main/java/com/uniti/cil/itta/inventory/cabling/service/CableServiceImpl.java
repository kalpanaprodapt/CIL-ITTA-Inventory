package com.uniti.cil.itta.inventory.cabling.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.event.CableEvents;
import com.uniti.cil.itta.inventory.cabling.mapper.CableMapper;
import com.uniti.cil.itta.inventory.cabling.model.Cable;
import com.uniti.cil.itta.inventory.cabling.repository.CableRepository;
import com.uniti.cil.itta.inventory.cabling.spec.CableSpecifications;
import com.uniti.cil.itta.inventory.cabling.validation.CableValidator;
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
public class CableServiceImpl implements CableService {

    private static final Logger log = LoggerFactory.getLogger(CableServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CableRepository repository;
    private final CableMapper mapper;
    private final CableValidator validator;
    private final EventPublisher events;

    public CableServiceImpl(CableRepository repository, CableMapper mapper, CableValidator validator,
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

    private Cable load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Cable", id));
    }

    @Override
    public CableResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CableResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cable", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CableResponse> search(CableSearchCriteria criteria, Pageable pageable) {
        Page<Cable> page = repository.findAll(CableSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CableSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CableResponse create(CableCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Cable", "code", request.getCode());
        }
        Cable entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Cable saved = repository.save(entity);
        CableResponse response = mapper.toResponse(saved);
        events.publish(CableEvents.created(response, actor()));
        log.info("Created Cable id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CableResponse update(UUID id, CableUpdateRequest request) {
        Cable entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Cable " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Cable", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Cable saved = repository.save(entity);
        CableResponse response = mapper.toResponse(saved);
        events.publish(CableEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CableEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CableResponse changeStatus(UUID id, LifecycleStatus status) {
        Cable entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Cable saved = repository.save(entity);
        CableResponse response = mapper.toResponse(saved);
        events.publish(CableEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Cable entity = load(id);
        CableResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CableEvents.deleted(snapshot, actor()));
        log.info("Deleted Cable id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CableResponse> bulkCreate(List<CableCreateRequest> requests) {
        BulkResult<CableResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Cable failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CableResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CableResponse> result = new BulkResult<>();
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
    public void exportCsv(CableSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CableMapper.CSV_HEADER));
        int page = 0;
        Page<Cable> chunk;
        do {
            chunk = repository.findAll(CableSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CableResponse> findByAEndPortId(UUID aEndPortId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAEndPortId(tenant(), aEndPortId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<CableResponse> findByZEndPortId(UUID zEndPortId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndZEndPortId(tenant(), zEndPortId, pageable).map(mapper::toResponse));
    }
}
