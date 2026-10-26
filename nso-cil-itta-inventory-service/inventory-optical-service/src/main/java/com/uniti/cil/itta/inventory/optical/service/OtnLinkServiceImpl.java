package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.event.OtnLinkEvents;
import com.uniti.cil.itta.inventory.optical.mapper.OtnLinkMapper;
import com.uniti.cil.itta.inventory.optical.model.OtnLink;
import com.uniti.cil.itta.inventory.optical.repository.OtnLinkRepository;
import com.uniti.cil.itta.inventory.optical.spec.OtnLinkSpecifications;
import com.uniti.cil.itta.inventory.optical.validation.OtnLinkValidator;
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
public class OtnLinkServiceImpl implements OtnLinkService {

    private static final Logger log = LoggerFactory.getLogger(OtnLinkServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final OtnLinkRepository repository;
    private final OtnLinkMapper mapper;
    private final OtnLinkValidator validator;
    private final EventPublisher events;

    public OtnLinkServiceImpl(OtnLinkRepository repository, OtnLinkMapper mapper, OtnLinkValidator validator,
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

    private OtnLink load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("OtnLink", id));
    }

    @Override
    public OtnLinkResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public OtnLinkResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("OtnLink", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<OtnLinkResponse> search(OtnLinkSearchCriteria criteria, Pageable pageable) {
        Page<OtnLink> page = repository.findAll(OtnLinkSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<OtnLinkSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public OtnLinkResponse create(OtnLinkCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("OtnLink", "code", request.getCode());
        }
        OtnLink entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        OtnLink saved = repository.save(entity);
        OtnLinkResponse response = mapper.toResponse(saved);
        events.publish(OtnLinkEvents.created(response, actor()));
        log.info("Created OtnLink id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public OtnLinkResponse update(UUID id, OtnLinkUpdateRequest request) {
        OtnLink entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "OtnLink " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("OtnLink", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        OtnLink saved = repository.save(entity);
        OtnLinkResponse response = mapper.toResponse(saved);
        events.publish(OtnLinkEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(OtnLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public OtnLinkResponse changeStatus(UUID id, LifecycleStatus status) {
        OtnLink entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        OtnLink saved = repository.save(entity);
        OtnLinkResponse response = mapper.toResponse(saved);
        events.publish(OtnLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        OtnLink entity = load(id);
        OtnLinkResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(OtnLinkEvents.deleted(snapshot, actor()));
        log.info("Deleted OtnLink id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<OtnLinkResponse> bulkCreate(List<OtnLinkCreateRequest> requests) {
        BulkResult<OtnLinkResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of OtnLink failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<OtnLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<OtnLinkResponse> result = new BulkResult<>();
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
    public void exportCsv(OtnLinkSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(OtnLinkMapper.CSV_HEADER));
        int page = 0;
        Page<OtnLink> chunk;
        do {
            chunk = repository.findAll(OtnLinkSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<OtnLinkResponse> findByANodeId(UUID aNodeId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndANodeId(tenant(), aNodeId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<OtnLinkResponse> findByZNodeId(UUID zNodeId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndZNodeId(tenant(), zNodeId, pageable).map(mapper::toResponse));
    }
}
