package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.event.UtilizationSampleEvents;
import com.uniti.cil.itta.inventory.capacity.mapper.UtilizationSampleMapper;
import com.uniti.cil.itta.inventory.capacity.model.UtilizationSample;
import com.uniti.cil.itta.inventory.capacity.repository.UtilizationSampleRepository;
import com.uniti.cil.itta.inventory.capacity.spec.UtilizationSampleSpecifications;
import com.uniti.cil.itta.inventory.capacity.validation.UtilizationSampleValidator;
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
public class UtilizationSampleServiceImpl implements UtilizationSampleService {

    private static final Logger log = LoggerFactory.getLogger(UtilizationSampleServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final UtilizationSampleRepository repository;
    private final UtilizationSampleMapper mapper;
    private final UtilizationSampleValidator validator;
    private final EventPublisher events;

    public UtilizationSampleServiceImpl(UtilizationSampleRepository repository, UtilizationSampleMapper mapper, UtilizationSampleValidator validator,
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

    private UtilizationSample load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("UtilizationSample", id));
    }

    @Override
    public UtilizationSampleResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public UtilizationSampleResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("UtilizationSample", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<UtilizationSampleResponse> search(UtilizationSampleSearchCriteria criteria, Pageable pageable) {
        Page<UtilizationSample> page = repository.findAll(UtilizationSampleSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<UtilizationSampleSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public UtilizationSampleResponse create(UtilizationSampleCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("UtilizationSample", "code", request.getCode());
        }
        UtilizationSample entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        UtilizationSample saved = repository.save(entity);
        UtilizationSampleResponse response = mapper.toResponse(saved);
        events.publish(UtilizationSampleEvents.created(response, actor()));
        log.info("Created UtilizationSample id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public UtilizationSampleResponse update(UUID id, UtilizationSampleUpdateRequest request) {
        UtilizationSample entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "UtilizationSample " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("UtilizationSample", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        UtilizationSample saved = repository.save(entity);
        UtilizationSampleResponse response = mapper.toResponse(saved);
        events.publish(UtilizationSampleEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(UtilizationSampleEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public UtilizationSampleResponse changeStatus(UUID id, LifecycleStatus status) {
        UtilizationSample entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        UtilizationSample saved = repository.save(entity);
        UtilizationSampleResponse response = mapper.toResponse(saved);
        events.publish(UtilizationSampleEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UtilizationSample entity = load(id);
        UtilizationSampleResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(UtilizationSampleEvents.deleted(snapshot, actor()));
        log.info("Deleted UtilizationSample id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<UtilizationSampleResponse> bulkCreate(List<UtilizationSampleCreateRequest> requests) {
        BulkResult<UtilizationSampleResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of UtilizationSample failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<UtilizationSampleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<UtilizationSampleResponse> result = new BulkResult<>();
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
    public void exportCsv(UtilizationSampleSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(UtilizationSampleMapper.CSV_HEADER));
        int page = 0;
        Page<UtilizationSample> chunk;
        do {
            chunk = repository.findAll(UtilizationSampleSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
