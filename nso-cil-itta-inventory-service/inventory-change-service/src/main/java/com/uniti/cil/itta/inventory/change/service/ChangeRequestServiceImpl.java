package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.event.ChangeRequestEvents;
import com.uniti.cil.itta.inventory.change.mapper.ChangeRequestMapper;
import com.uniti.cil.itta.inventory.change.model.ChangeRequest;
import com.uniti.cil.itta.inventory.change.repository.ChangeRequestRepository;
import com.uniti.cil.itta.inventory.change.spec.ChangeRequestSpecifications;
import com.uniti.cil.itta.inventory.change.validation.ChangeRequestValidator;
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
public class ChangeRequestServiceImpl implements ChangeRequestService {

    private static final Logger log = LoggerFactory.getLogger(ChangeRequestServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final ChangeRequestRepository repository;
    private final ChangeRequestMapper mapper;
    private final ChangeRequestValidator validator;
    private final EventPublisher events;

    public ChangeRequestServiceImpl(ChangeRequestRepository repository, ChangeRequestMapper mapper, ChangeRequestValidator validator,
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

    private ChangeRequest load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("ChangeRequest", id));
    }

    @Override
    public ChangeRequestResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public ChangeRequestResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ChangeRequest", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<ChangeRequestResponse> search(ChangeRequestSearchCriteria criteria, Pageable pageable) {
        Page<ChangeRequest> page = repository.findAll(ChangeRequestSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<ChangeRequestSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public ChangeRequestResponse create(ChangeRequestCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("ChangeRequest", "code", request.getCode());
        }
        ChangeRequest entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        ChangeRequest saved = repository.save(entity);
        ChangeRequestResponse response = mapper.toResponse(saved);
        events.publish(ChangeRequestEvents.created(response, actor()));
        log.info("Created ChangeRequest id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public ChangeRequestResponse update(UUID id, ChangeRequestUpdateRequest request) {
        ChangeRequest entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "ChangeRequest " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("ChangeRequest", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        ChangeRequest saved = repository.save(entity);
        ChangeRequestResponse response = mapper.toResponse(saved);
        events.publish(ChangeRequestEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(ChangeRequestEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public ChangeRequestResponse changeStatus(UUID id, LifecycleStatus status) {
        ChangeRequest entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        ChangeRequest saved = repository.save(entity);
        ChangeRequestResponse response = mapper.toResponse(saved);
        events.publish(ChangeRequestEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ChangeRequest entity = load(id);
        ChangeRequestResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(ChangeRequestEvents.deleted(snapshot, actor()));
        log.info("Deleted ChangeRequest id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<ChangeRequestResponse> bulkCreate(List<ChangeRequestCreateRequest> requests) {
        BulkResult<ChangeRequestResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of ChangeRequest failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<ChangeRequestResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<ChangeRequestResponse> result = new BulkResult<>();
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
    public void exportCsv(ChangeRequestSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(ChangeRequestMapper.CSV_HEADER));
        int page = 0;
        Page<ChangeRequest> chunk;
        do {
            chunk = repository.findAll(ChangeRequestSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
