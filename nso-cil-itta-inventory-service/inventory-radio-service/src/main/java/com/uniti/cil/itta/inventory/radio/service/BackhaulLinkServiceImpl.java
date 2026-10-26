package com.uniti.cil.itta.inventory.radio.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.event.BackhaulLinkEvents;
import com.uniti.cil.itta.inventory.radio.mapper.BackhaulLinkMapper;
import com.uniti.cil.itta.inventory.radio.model.BackhaulLink;
import com.uniti.cil.itta.inventory.radio.repository.BackhaulLinkRepository;
import com.uniti.cil.itta.inventory.radio.spec.BackhaulLinkSpecifications;
import com.uniti.cil.itta.inventory.radio.validation.BackhaulLinkValidator;
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
public class BackhaulLinkServiceImpl implements BackhaulLinkService {

    private static final Logger log = LoggerFactory.getLogger(BackhaulLinkServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final BackhaulLinkRepository repository;
    private final BackhaulLinkMapper mapper;
    private final BackhaulLinkValidator validator;
    private final EventPublisher events;

    public BackhaulLinkServiceImpl(BackhaulLinkRepository repository, BackhaulLinkMapper mapper, BackhaulLinkValidator validator,
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

    private BackhaulLink load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("BackhaulLink", id));
    }

    @Override
    public BackhaulLinkResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public BackhaulLinkResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("BackhaulLink", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<BackhaulLinkResponse> search(BackhaulLinkSearchCriteria criteria, Pageable pageable) {
        Page<BackhaulLink> page = repository.findAll(BackhaulLinkSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<BackhaulLinkSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public BackhaulLinkResponse create(BackhaulLinkCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("BackhaulLink", "code", request.getCode());
        }
        BackhaulLink entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        BackhaulLink saved = repository.save(entity);
        BackhaulLinkResponse response = mapper.toResponse(saved);
        events.publish(BackhaulLinkEvents.created(response, actor()));
        log.info("Created BackhaulLink id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public BackhaulLinkResponse update(UUID id, BackhaulLinkUpdateRequest request) {
        BackhaulLink entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "BackhaulLink " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("BackhaulLink", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        BackhaulLink saved = repository.save(entity);
        BackhaulLinkResponse response = mapper.toResponse(saved);
        events.publish(BackhaulLinkEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(BackhaulLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public BackhaulLinkResponse changeStatus(UUID id, LifecycleStatus status) {
        BackhaulLink entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        BackhaulLink saved = repository.save(entity);
        BackhaulLinkResponse response = mapper.toResponse(saved);
        events.publish(BackhaulLinkEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        BackhaulLink entity = load(id);
        BackhaulLinkResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(BackhaulLinkEvents.deleted(snapshot, actor()));
        log.info("Deleted BackhaulLink id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<BackhaulLinkResponse> bulkCreate(List<BackhaulLinkCreateRequest> requests) {
        BulkResult<BackhaulLinkResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of BackhaulLink failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<BackhaulLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<BackhaulLinkResponse> result = new BulkResult<>();
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
    public void exportCsv(BackhaulLinkSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(BackhaulLinkMapper.CSV_HEADER));
        int page = 0;
        Page<BackhaulLink> chunk;
        do {
            chunk = repository.findAll(BackhaulLinkSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<BackhaulLinkResponse> findByBaseStationId(UUID baseStationId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndBaseStationId(tenant(), baseStationId, pageable).map(mapper::toResponse));
    }
}
