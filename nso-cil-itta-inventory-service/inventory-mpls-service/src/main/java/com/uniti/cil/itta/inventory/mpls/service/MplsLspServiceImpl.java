package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.event.MplsLspEvents;
import com.uniti.cil.itta.inventory.mpls.mapper.MplsLspMapper;
import com.uniti.cil.itta.inventory.mpls.model.MplsLsp;
import com.uniti.cil.itta.inventory.mpls.repository.MplsLspRepository;
import com.uniti.cil.itta.inventory.mpls.spec.MplsLspSpecifications;
import com.uniti.cil.itta.inventory.mpls.validation.MplsLspValidator;
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
public class MplsLspServiceImpl implements MplsLspService {

    private static final Logger log = LoggerFactory.getLogger(MplsLspServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final MplsLspRepository repository;
    private final MplsLspMapper mapper;
    private final MplsLspValidator validator;
    private final EventPublisher events;

    public MplsLspServiceImpl(MplsLspRepository repository, MplsLspMapper mapper, MplsLspValidator validator,
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

    private MplsLsp load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("MplsLsp", id));
    }

    @Override
    public MplsLspResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public MplsLspResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("MplsLsp", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<MplsLspResponse> search(MplsLspSearchCriteria criteria, Pageable pageable) {
        Page<MplsLsp> page = repository.findAll(MplsLspSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<MplsLspSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public MplsLspResponse create(MplsLspCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("MplsLsp", "code", request.getCode());
        }
        MplsLsp entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        MplsLsp saved = repository.save(entity);
        MplsLspResponse response = mapper.toResponse(saved);
        events.publish(MplsLspEvents.created(response, actor()));
        log.info("Created MplsLsp id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public MplsLspResponse update(UUID id, MplsLspUpdateRequest request) {
        MplsLsp entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "MplsLsp " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("MplsLsp", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        MplsLsp saved = repository.save(entity);
        MplsLspResponse response = mapper.toResponse(saved);
        events.publish(MplsLspEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(MplsLspEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public MplsLspResponse changeStatus(UUID id, LifecycleStatus status) {
        MplsLsp entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        MplsLsp saved = repository.save(entity);
        MplsLspResponse response = mapper.toResponse(saved);
        events.publish(MplsLspEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        MplsLsp entity = load(id);
        MplsLspResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(MplsLspEvents.deleted(snapshot, actor()));
        log.info("Deleted MplsLsp id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<MplsLspResponse> bulkCreate(List<MplsLspCreateRequest> requests) {
        BulkResult<MplsLspResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of MplsLsp failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<MplsLspResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<MplsLspResponse> result = new BulkResult<>();
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
    public void exportCsv(MplsLspSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(MplsLspMapper.CSV_HEADER));
        int page = 0;
        Page<MplsLsp> chunk;
        do {
            chunk = repository.findAll(MplsLspSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<MplsLspResponse> findByHeadEndId(UUID headEndId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndHeadEndId(tenant(), headEndId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<MplsLspResponse> findByTailEndId(UUID tailEndId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndTailEndId(tenant(), tailEndId, pageable).map(mapper::toResponse));
    }
}
