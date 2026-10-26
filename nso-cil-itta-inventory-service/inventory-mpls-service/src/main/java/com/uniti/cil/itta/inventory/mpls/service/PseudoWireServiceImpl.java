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
import com.uniti.cil.itta.inventory.mpls.event.PseudoWireEvents;
import com.uniti.cil.itta.inventory.mpls.mapper.PseudoWireMapper;
import com.uniti.cil.itta.inventory.mpls.model.PseudoWire;
import com.uniti.cil.itta.inventory.mpls.repository.PseudoWireRepository;
import com.uniti.cil.itta.inventory.mpls.spec.PseudoWireSpecifications;
import com.uniti.cil.itta.inventory.mpls.validation.PseudoWireValidator;
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
public class PseudoWireServiceImpl implements PseudoWireService {

    private static final Logger log = LoggerFactory.getLogger(PseudoWireServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PseudoWireRepository repository;
    private final PseudoWireMapper mapper;
    private final PseudoWireValidator validator;
    private final EventPublisher events;

    public PseudoWireServiceImpl(PseudoWireRepository repository, PseudoWireMapper mapper, PseudoWireValidator validator,
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

    private PseudoWire load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PseudoWire", id));
    }

    @Override
    public PseudoWireResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PseudoWireResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PseudoWire", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PseudoWireResponse> search(PseudoWireSearchCriteria criteria, Pageable pageable) {
        Page<PseudoWire> page = repository.findAll(PseudoWireSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PseudoWireSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PseudoWireResponse create(PseudoWireCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PseudoWire", "code", request.getCode());
        }
        PseudoWire entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PseudoWire saved = repository.save(entity);
        PseudoWireResponse response = mapper.toResponse(saved);
        events.publish(PseudoWireEvents.created(response, actor()));
        log.info("Created PseudoWire id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PseudoWireResponse update(UUID id, PseudoWireUpdateRequest request) {
        PseudoWire entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PseudoWire " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PseudoWire", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PseudoWire saved = repository.save(entity);
        PseudoWireResponse response = mapper.toResponse(saved);
        events.publish(PseudoWireEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PseudoWireEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PseudoWireResponse changeStatus(UUID id, LifecycleStatus status) {
        PseudoWire entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PseudoWire saved = repository.save(entity);
        PseudoWireResponse response = mapper.toResponse(saved);
        events.publish(PseudoWireEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PseudoWire entity = load(id);
        PseudoWireResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PseudoWireEvents.deleted(snapshot, actor()));
        log.info("Deleted PseudoWire id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PseudoWireResponse> bulkCreate(List<PseudoWireCreateRequest> requests) {
        BulkResult<PseudoWireResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PseudoWire failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PseudoWireResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PseudoWireResponse> result = new BulkResult<>();
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
    public void exportCsv(PseudoWireSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PseudoWireMapper.CSV_HEADER));
        int page = 0;
        Page<PseudoWire> chunk;
        do {
            chunk = repository.findAll(PseudoWireSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PseudoWireResponse> findByAEndId(UUID aEndId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAEndId(tenant(), aEndId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<PseudoWireResponse> findByZEndId(UUID zEndId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndZEndId(tenant(), zEndId, pageable).map(mapper::toResponse));
    }
}
