package com.uniti.cil.itta.inventory.vendor.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.event.HardwareModelEvents;
import com.uniti.cil.itta.inventory.vendor.mapper.HardwareModelMapper;
import com.uniti.cil.itta.inventory.vendor.model.HardwareModel;
import com.uniti.cil.itta.inventory.vendor.repository.HardwareModelRepository;
import com.uniti.cil.itta.inventory.vendor.spec.HardwareModelSpecifications;
import com.uniti.cil.itta.inventory.vendor.validation.HardwareModelValidator;
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
public class HardwareModelServiceImpl implements HardwareModelService {

    private static final Logger log = LoggerFactory.getLogger(HardwareModelServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final HardwareModelRepository repository;
    private final HardwareModelMapper mapper;
    private final HardwareModelValidator validator;
    private final EventPublisher events;

    public HardwareModelServiceImpl(HardwareModelRepository repository, HardwareModelMapper mapper, HardwareModelValidator validator,
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

    private HardwareModel load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("HardwareModel", id));
    }

    @Override
    public HardwareModelResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public HardwareModelResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("HardwareModel", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<HardwareModelResponse> search(HardwareModelSearchCriteria criteria, Pageable pageable) {
        Page<HardwareModel> page = repository.findAll(HardwareModelSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<HardwareModelSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public HardwareModelResponse create(HardwareModelCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("HardwareModel", "code", request.getCode());
        }
        HardwareModel entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        HardwareModel saved = repository.save(entity);
        HardwareModelResponse response = mapper.toResponse(saved);
        events.publish(HardwareModelEvents.created(response, actor()));
        log.info("Created HardwareModel id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public HardwareModelResponse update(UUID id, HardwareModelUpdateRequest request) {
        HardwareModel entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "HardwareModel " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("HardwareModel", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        HardwareModel saved = repository.save(entity);
        HardwareModelResponse response = mapper.toResponse(saved);
        events.publish(HardwareModelEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(HardwareModelEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public HardwareModelResponse changeStatus(UUID id, LifecycleStatus status) {
        HardwareModel entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        HardwareModel saved = repository.save(entity);
        HardwareModelResponse response = mapper.toResponse(saved);
        events.publish(HardwareModelEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        HardwareModel entity = load(id);
        HardwareModelResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(HardwareModelEvents.deleted(snapshot, actor()));
        log.info("Deleted HardwareModel id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<HardwareModelResponse> bulkCreate(List<HardwareModelCreateRequest> requests) {
        BulkResult<HardwareModelResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of HardwareModel failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<HardwareModelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<HardwareModelResponse> result = new BulkResult<>();
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
    public void exportCsv(HardwareModelSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(HardwareModelMapper.CSV_HEADER));
        int page = 0;
        Page<HardwareModel> chunk;
        do {
            chunk = repository.findAll(HardwareModelSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<HardwareModelResponse> findByVendorId(UUID vendorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVendorId(tenant(), vendorId, pageable).map(mapper::toResponse));
    }
}
