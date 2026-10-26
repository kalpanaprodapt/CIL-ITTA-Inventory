package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.event.OspfAreaEvents;
import com.uniti.cil.itta.inventory.switching.mapper.OspfAreaMapper;
import com.uniti.cil.itta.inventory.switching.model.OspfArea;
import com.uniti.cil.itta.inventory.switching.repository.OspfAreaRepository;
import com.uniti.cil.itta.inventory.switching.spec.OspfAreaSpecifications;
import com.uniti.cil.itta.inventory.switching.validation.OspfAreaValidator;
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
public class OspfAreaServiceImpl implements OspfAreaService {

    private static final Logger log = LoggerFactory.getLogger(OspfAreaServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final OspfAreaRepository repository;
    private final OspfAreaMapper mapper;
    private final OspfAreaValidator validator;
    private final EventPublisher events;

    public OspfAreaServiceImpl(OspfAreaRepository repository, OspfAreaMapper mapper, OspfAreaValidator validator,
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

    private OspfArea load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("OspfArea", id));
    }

    @Override
    public OspfAreaResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public OspfAreaResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("OspfArea", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<OspfAreaResponse> search(OspfAreaSearchCriteria criteria, Pageable pageable) {
        Page<OspfArea> page = repository.findAll(OspfAreaSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<OspfAreaSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public OspfAreaResponse create(OspfAreaCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("OspfArea", "code", request.getCode());
        }
        OspfArea entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        OspfArea saved = repository.save(entity);
        OspfAreaResponse response = mapper.toResponse(saved);
        events.publish(OspfAreaEvents.created(response, actor()));
        log.info("Created OspfArea id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public OspfAreaResponse update(UUID id, OspfAreaUpdateRequest request) {
        OspfArea entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "OspfArea " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("OspfArea", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        OspfArea saved = repository.save(entity);
        OspfAreaResponse response = mapper.toResponse(saved);
        events.publish(OspfAreaEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(OspfAreaEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public OspfAreaResponse changeStatus(UUID id, LifecycleStatus status) {
        OspfArea entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        OspfArea saved = repository.save(entity);
        OspfAreaResponse response = mapper.toResponse(saved);
        events.publish(OspfAreaEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        OspfArea entity = load(id);
        OspfAreaResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(OspfAreaEvents.deleted(snapshot, actor()));
        log.info("Deleted OspfArea id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<OspfAreaResponse> bulkCreate(List<OspfAreaCreateRequest> requests) {
        BulkResult<OspfAreaResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of OspfArea failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<OspfAreaResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<OspfAreaResponse> result = new BulkResult<>();
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
    public void exportCsv(OspfAreaSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(OspfAreaMapper.CSV_HEADER));
        int page = 0;
        Page<OspfArea> chunk;
        do {
            chunk = repository.findAll(OspfAreaSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
