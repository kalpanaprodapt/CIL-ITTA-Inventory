package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.event.DiscoveryProfileEvents;
import com.uniti.cil.itta.inventory.discovery.mapper.DiscoveryProfileMapper;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryProfile;
import com.uniti.cil.itta.inventory.discovery.repository.DiscoveryProfileRepository;
import com.uniti.cil.itta.inventory.discovery.spec.DiscoveryProfileSpecifications;
import com.uniti.cil.itta.inventory.discovery.validation.DiscoveryProfileValidator;
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
public class DiscoveryProfileServiceImpl implements DiscoveryProfileService {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryProfileServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final DiscoveryProfileRepository repository;
    private final DiscoveryProfileMapper mapper;
    private final DiscoveryProfileValidator validator;
    private final EventPublisher events;

    public DiscoveryProfileServiceImpl(DiscoveryProfileRepository repository, DiscoveryProfileMapper mapper, DiscoveryProfileValidator validator,
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

    private DiscoveryProfile load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveryProfile", id));
    }

    @Override
    public DiscoveryProfileResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public DiscoveryProfileResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DiscoveryProfile", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<DiscoveryProfileResponse> search(DiscoveryProfileSearchCriteria criteria, Pageable pageable) {
        Page<DiscoveryProfile> page = repository.findAll(DiscoveryProfileSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<DiscoveryProfileSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public DiscoveryProfileResponse create(DiscoveryProfileCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("DiscoveryProfile", "code", request.getCode());
        }
        DiscoveryProfile entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        DiscoveryProfile saved = repository.save(entity);
        DiscoveryProfileResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryProfileEvents.created(response, actor()));
        log.info("Created DiscoveryProfile id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public DiscoveryProfileResponse update(UUID id, DiscoveryProfileUpdateRequest request) {
        DiscoveryProfile entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "DiscoveryProfile " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("DiscoveryProfile", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        DiscoveryProfile saved = repository.save(entity);
        DiscoveryProfileResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryProfileEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(DiscoveryProfileEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public DiscoveryProfileResponse changeStatus(UUID id, LifecycleStatus status) {
        DiscoveryProfile entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        DiscoveryProfile saved = repository.save(entity);
        DiscoveryProfileResponse response = mapper.toResponse(saved);
        events.publish(DiscoveryProfileEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DiscoveryProfile entity = load(id);
        DiscoveryProfileResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(DiscoveryProfileEvents.deleted(snapshot, actor()));
        log.info("Deleted DiscoveryProfile id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<DiscoveryProfileResponse> bulkCreate(List<DiscoveryProfileCreateRequest> requests) {
        BulkResult<DiscoveryProfileResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of DiscoveryProfile failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<DiscoveryProfileResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<DiscoveryProfileResponse> result = new BulkResult<>();
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
    public void exportCsv(DiscoveryProfileSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(DiscoveryProfileMapper.CSV_HEADER));
        int page = 0;
        Page<DiscoveryProfile> chunk;
        do {
            chunk = repository.findAll(DiscoveryProfileSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
