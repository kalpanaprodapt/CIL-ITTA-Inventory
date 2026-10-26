package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.event.VnfEvents;
import com.uniti.cil.itta.inventory.virtual.mapper.VnfMapper;
import com.uniti.cil.itta.inventory.virtual.model.Vnf;
import com.uniti.cil.itta.inventory.virtual.repository.VnfRepository;
import com.uniti.cil.itta.inventory.virtual.spec.VnfSpecifications;
import com.uniti.cil.itta.inventory.virtual.validation.VnfValidator;
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
public class VnfServiceImpl implements VnfService {

    private static final Logger log = LoggerFactory.getLogger(VnfServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VnfRepository repository;
    private final VnfMapper mapper;
    private final VnfValidator validator;
    private final EventPublisher events;

    public VnfServiceImpl(VnfRepository repository, VnfMapper mapper, VnfValidator validator,
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

    private Vnf load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Vnf", id));
    }

    @Override
    public VnfResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VnfResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Vnf", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VnfResponse> search(VnfSearchCriteria criteria, Pageable pageable) {
        Page<Vnf> page = repository.findAll(VnfSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VnfSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VnfResponse create(VnfCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Vnf", "code", request.getCode());
        }
        Vnf entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Vnf saved = repository.save(entity);
        VnfResponse response = mapper.toResponse(saved);
        events.publish(VnfEvents.created(response, actor()));
        log.info("Created Vnf id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VnfResponse update(UUID id, VnfUpdateRequest request) {
        Vnf entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Vnf " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Vnf", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Vnf saved = repository.save(entity);
        VnfResponse response = mapper.toResponse(saved);
        events.publish(VnfEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VnfEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VnfResponse changeStatus(UUID id, LifecycleStatus status) {
        Vnf entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Vnf saved = repository.save(entity);
        VnfResponse response = mapper.toResponse(saved);
        events.publish(VnfEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Vnf entity = load(id);
        VnfResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VnfEvents.deleted(snapshot, actor()));
        log.info("Deleted Vnf id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VnfResponse> bulkCreate(List<VnfCreateRequest> requests) {
        BulkResult<VnfResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Vnf failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VnfResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VnfResponse> result = new BulkResult<>();
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
    public void exportCsv(VnfSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VnfMapper.CSV_HEADER));
        int page = 0;
        Page<Vnf> chunk;
        do {
            chunk = repository.findAll(VnfSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
