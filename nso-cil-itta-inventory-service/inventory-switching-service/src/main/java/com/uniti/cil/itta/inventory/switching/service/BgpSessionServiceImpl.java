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
import com.uniti.cil.itta.inventory.switching.event.BgpSessionEvents;
import com.uniti.cil.itta.inventory.switching.mapper.BgpSessionMapper;
import com.uniti.cil.itta.inventory.switching.model.BgpSession;
import com.uniti.cil.itta.inventory.switching.repository.BgpSessionRepository;
import com.uniti.cil.itta.inventory.switching.spec.BgpSessionSpecifications;
import com.uniti.cil.itta.inventory.switching.validation.BgpSessionValidator;
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
public class BgpSessionServiceImpl implements BgpSessionService {

    private static final Logger log = LoggerFactory.getLogger(BgpSessionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final BgpSessionRepository repository;
    private final BgpSessionMapper mapper;
    private final BgpSessionValidator validator;
    private final EventPublisher events;

    public BgpSessionServiceImpl(BgpSessionRepository repository, BgpSessionMapper mapper, BgpSessionValidator validator,
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

    private BgpSession load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("BgpSession", id));
    }

    @Override
    public BgpSessionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public BgpSessionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("BgpSession", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<BgpSessionResponse> search(BgpSessionSearchCriteria criteria, Pageable pageable) {
        Page<BgpSession> page = repository.findAll(BgpSessionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<BgpSessionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public BgpSessionResponse create(BgpSessionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("BgpSession", "code", request.getCode());
        }
        BgpSession entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        BgpSession saved = repository.save(entity);
        BgpSessionResponse response = mapper.toResponse(saved);
        events.publish(BgpSessionEvents.created(response, actor()));
        log.info("Created BgpSession id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public BgpSessionResponse update(UUID id, BgpSessionUpdateRequest request) {
        BgpSession entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "BgpSession " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("BgpSession", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        BgpSession saved = repository.save(entity);
        BgpSessionResponse response = mapper.toResponse(saved);
        events.publish(BgpSessionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(BgpSessionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public BgpSessionResponse changeStatus(UUID id, LifecycleStatus status) {
        BgpSession entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        BgpSession saved = repository.save(entity);
        BgpSessionResponse response = mapper.toResponse(saved);
        events.publish(BgpSessionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        BgpSession entity = load(id);
        BgpSessionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(BgpSessionEvents.deleted(snapshot, actor()));
        log.info("Deleted BgpSession id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<BgpSessionResponse> bulkCreate(List<BgpSessionCreateRequest> requests) {
        BulkResult<BgpSessionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of BgpSession failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<BgpSessionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<BgpSessionResponse> result = new BulkResult<>();
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
    public void exportCsv(BgpSessionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(BgpSessionMapper.CSV_HEADER));
        int page = 0;
        Page<BgpSession> chunk;
        do {
            chunk = repository.findAll(BgpSessionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<BgpSessionResponse> findByLocalDeviceId(UUID localDeviceId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndLocalDeviceId(tenant(), localDeviceId, pageable).map(mapper::toResponse));
    }
}
