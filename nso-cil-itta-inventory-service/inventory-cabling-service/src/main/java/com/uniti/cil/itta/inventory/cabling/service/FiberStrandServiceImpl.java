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
import com.uniti.cil.itta.inventory.cabling.event.FiberStrandEvents;
import com.uniti.cil.itta.inventory.cabling.mapper.FiberStrandMapper;
import com.uniti.cil.itta.inventory.cabling.model.FiberStrand;
import com.uniti.cil.itta.inventory.cabling.repository.FiberStrandRepository;
import com.uniti.cil.itta.inventory.cabling.spec.FiberStrandSpecifications;
import com.uniti.cil.itta.inventory.cabling.validation.FiberStrandValidator;
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
public class FiberStrandServiceImpl implements FiberStrandService {

    private static final Logger log = LoggerFactory.getLogger(FiberStrandServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final FiberStrandRepository repository;
    private final FiberStrandMapper mapper;
    private final FiberStrandValidator validator;
    private final EventPublisher events;

    public FiberStrandServiceImpl(FiberStrandRepository repository, FiberStrandMapper mapper, FiberStrandValidator validator,
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

    private FiberStrand load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("FiberStrand", id));
    }

    @Override
    public FiberStrandResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public FiberStrandResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("FiberStrand", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<FiberStrandResponse> search(FiberStrandSearchCriteria criteria, Pageable pageable) {
        Page<FiberStrand> page = repository.findAll(FiberStrandSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<FiberStrandSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public FiberStrandResponse create(FiberStrandCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("FiberStrand", "code", request.getCode());
        }
        FiberStrand entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        FiberStrand saved = repository.save(entity);
        FiberStrandResponse response = mapper.toResponse(saved);
        events.publish(FiberStrandEvents.created(response, actor()));
        log.info("Created FiberStrand id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public FiberStrandResponse update(UUID id, FiberStrandUpdateRequest request) {
        FiberStrand entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "FiberStrand " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("FiberStrand", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        FiberStrand saved = repository.save(entity);
        FiberStrandResponse response = mapper.toResponse(saved);
        events.publish(FiberStrandEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(FiberStrandEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public FiberStrandResponse changeStatus(UUID id, LifecycleStatus status) {
        FiberStrand entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        FiberStrand saved = repository.save(entity);
        FiberStrandResponse response = mapper.toResponse(saved);
        events.publish(FiberStrandEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        FiberStrand entity = load(id);
        FiberStrandResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(FiberStrandEvents.deleted(snapshot, actor()));
        log.info("Deleted FiberStrand id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<FiberStrandResponse> bulkCreate(List<FiberStrandCreateRequest> requests) {
        BulkResult<FiberStrandResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of FiberStrand failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<FiberStrandResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<FiberStrandResponse> result = new BulkResult<>();
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
    public void exportCsv(FiberStrandSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(FiberStrandMapper.CSV_HEADER));
        int page = 0;
        Page<FiberStrand> chunk;
        do {
            chunk = repository.findAll(FiberStrandSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<FiberStrandResponse> findByCableId(UUID cableId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCableId(tenant(), cableId, pageable).map(mapper::toResponse));
    }
}
