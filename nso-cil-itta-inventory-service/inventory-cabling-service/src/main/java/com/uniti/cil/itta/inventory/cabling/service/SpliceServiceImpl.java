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
import com.uniti.cil.itta.inventory.cabling.event.SpliceEvents;
import com.uniti.cil.itta.inventory.cabling.mapper.SpliceMapper;
import com.uniti.cil.itta.inventory.cabling.model.Splice;
import com.uniti.cil.itta.inventory.cabling.repository.SpliceRepository;
import com.uniti.cil.itta.inventory.cabling.spec.SpliceSpecifications;
import com.uniti.cil.itta.inventory.cabling.validation.SpliceValidator;
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
public class SpliceServiceImpl implements SpliceService {

    private static final Logger log = LoggerFactory.getLogger(SpliceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SpliceRepository repository;
    private final SpliceMapper mapper;
    private final SpliceValidator validator;
    private final EventPublisher events;

    public SpliceServiceImpl(SpliceRepository repository, SpliceMapper mapper, SpliceValidator validator,
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

    private Splice load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Splice", id));
    }

    @Override
    public SpliceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SpliceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Splice", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SpliceResponse> search(SpliceSearchCriteria criteria, Pageable pageable) {
        Page<Splice> page = repository.findAll(SpliceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SpliceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SpliceResponse create(SpliceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Splice", "code", request.getCode());
        }
        Splice entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Splice saved = repository.save(entity);
        SpliceResponse response = mapper.toResponse(saved);
        events.publish(SpliceEvents.created(response, actor()));
        log.info("Created Splice id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SpliceResponse update(UUID id, SpliceUpdateRequest request) {
        Splice entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Splice " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Splice", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Splice saved = repository.save(entity);
        SpliceResponse response = mapper.toResponse(saved);
        events.publish(SpliceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SpliceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SpliceResponse changeStatus(UUID id, LifecycleStatus status) {
        Splice entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Splice saved = repository.save(entity);
        SpliceResponse response = mapper.toResponse(saved);
        events.publish(SpliceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Splice entity = load(id);
        SpliceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SpliceEvents.deleted(snapshot, actor()));
        log.info("Deleted Splice id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SpliceResponse> bulkCreate(List<SpliceCreateRequest> requests) {
        BulkResult<SpliceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Splice failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SpliceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SpliceResponse> result = new BulkResult<>();
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
    public void exportCsv(SpliceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SpliceMapper.CSV_HEADER));
        int page = 0;
        Page<Splice> chunk;
        do {
            chunk = repository.findAll(SpliceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SpliceResponse> findByAStrandId(UUID aStrandId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAStrandId(tenant(), aStrandId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<SpliceResponse> findByZStrandId(UUID zStrandId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndZStrandId(tenant(), zStrandId, pageable).map(mapper::toResponse));
    }
}
