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
import com.uniti.cil.itta.inventory.vendor.event.VendorEvents;
import com.uniti.cil.itta.inventory.vendor.mapper.VendorMapper;
import com.uniti.cil.itta.inventory.vendor.model.Vendor;
import com.uniti.cil.itta.inventory.vendor.repository.VendorRepository;
import com.uniti.cil.itta.inventory.vendor.spec.VendorSpecifications;
import com.uniti.cil.itta.inventory.vendor.validation.VendorValidator;
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
public class VendorServiceImpl implements VendorService {

    private static final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VendorRepository repository;
    private final VendorMapper mapper;
    private final VendorValidator validator;
    private final EventPublisher events;

    public VendorServiceImpl(VendorRepository repository, VendorMapper mapper, VendorValidator validator,
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

    private Vendor load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", id));
    }

    @Override
    public VendorResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VendorResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VendorResponse> search(VendorSearchCriteria criteria, Pageable pageable) {
        Page<Vendor> page = repository.findAll(VendorSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VendorSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VendorResponse create(VendorCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Vendor", "code", request.getCode());
        }
        Vendor entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Vendor saved = repository.save(entity);
        VendorResponse response = mapper.toResponse(saved);
        events.publish(VendorEvents.created(response, actor()));
        log.info("Created Vendor id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VendorResponse update(UUID id, VendorUpdateRequest request) {
        Vendor entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Vendor " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Vendor", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Vendor saved = repository.save(entity);
        VendorResponse response = mapper.toResponse(saved);
        events.publish(VendorEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VendorEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VendorResponse changeStatus(UUID id, LifecycleStatus status) {
        Vendor entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Vendor saved = repository.save(entity);
        VendorResponse response = mapper.toResponse(saved);
        events.publish(VendorEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Vendor entity = load(id);
        VendorResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VendorEvents.deleted(snapshot, actor()));
        log.info("Deleted Vendor id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VendorResponse> bulkCreate(List<VendorCreateRequest> requests) {
        BulkResult<VendorResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Vendor failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VendorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VendorResponse> result = new BulkResult<>();
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
    public void exportCsv(VendorSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VendorMapper.CSV_HEADER));
        int page = 0;
        Page<Vendor> chunk;
        do {
            chunk = repository.findAll(VendorSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
