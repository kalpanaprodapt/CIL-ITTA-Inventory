package com.uniti.cil.itta.inventory.customer.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.event.SlaProfileEvents;
import com.uniti.cil.itta.inventory.customer.mapper.SlaProfileMapper;
import com.uniti.cil.itta.inventory.customer.model.SlaProfile;
import com.uniti.cil.itta.inventory.customer.repository.SlaProfileRepository;
import com.uniti.cil.itta.inventory.customer.spec.SlaProfileSpecifications;
import com.uniti.cil.itta.inventory.customer.validation.SlaProfileValidator;
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
public class SlaProfileServiceImpl implements SlaProfileService {

    private static final Logger log = LoggerFactory.getLogger(SlaProfileServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SlaProfileRepository repository;
    private final SlaProfileMapper mapper;
    private final SlaProfileValidator validator;
    private final EventPublisher events;

    public SlaProfileServiceImpl(SlaProfileRepository repository, SlaProfileMapper mapper, SlaProfileValidator validator,
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

    private SlaProfile load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("SlaProfile", id));
    }

    @Override
    public SlaProfileResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SlaProfileResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("SlaProfile", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SlaProfileResponse> search(SlaProfileSearchCriteria criteria, Pageable pageable) {
        Page<SlaProfile> page = repository.findAll(SlaProfileSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SlaProfileSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SlaProfileResponse create(SlaProfileCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("SlaProfile", "code", request.getCode());
        }
        SlaProfile entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        SlaProfile saved = repository.save(entity);
        SlaProfileResponse response = mapper.toResponse(saved);
        events.publish(SlaProfileEvents.created(response, actor()));
        log.info("Created SlaProfile id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SlaProfileResponse update(UUID id, SlaProfileUpdateRequest request) {
        SlaProfile entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "SlaProfile " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("SlaProfile", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        SlaProfile saved = repository.save(entity);
        SlaProfileResponse response = mapper.toResponse(saved);
        events.publish(SlaProfileEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SlaProfileEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SlaProfileResponse changeStatus(UUID id, LifecycleStatus status) {
        SlaProfile entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        SlaProfile saved = repository.save(entity);
        SlaProfileResponse response = mapper.toResponse(saved);
        events.publish(SlaProfileEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SlaProfile entity = load(id);
        SlaProfileResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SlaProfileEvents.deleted(snapshot, actor()));
        log.info("Deleted SlaProfile id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SlaProfileResponse> bulkCreate(List<SlaProfileCreateRequest> requests) {
        BulkResult<SlaProfileResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of SlaProfile failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SlaProfileResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SlaProfileResponse> result = new BulkResult<>();
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
    public void exportCsv(SlaProfileSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SlaProfileMapper.CSV_HEADER));
        int page = 0;
        Page<SlaProfile> chunk;
        do {
            chunk = repository.findAll(SlaProfileSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
