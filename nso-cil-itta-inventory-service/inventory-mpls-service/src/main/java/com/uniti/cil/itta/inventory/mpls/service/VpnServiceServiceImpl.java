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
import com.uniti.cil.itta.inventory.mpls.event.VpnServiceEvents;
import com.uniti.cil.itta.inventory.mpls.mapper.VpnServiceMapper;
import com.uniti.cil.itta.inventory.mpls.model.VpnService;
import com.uniti.cil.itta.inventory.mpls.repository.VpnServiceRepository;
import com.uniti.cil.itta.inventory.mpls.spec.VpnServiceSpecifications;
import com.uniti.cil.itta.inventory.mpls.validation.VpnServiceValidator;
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
public class VpnServiceServiceImpl implements VpnServiceService {

    private static final Logger log = LoggerFactory.getLogger(VpnServiceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VpnServiceRepository repository;
    private final VpnServiceMapper mapper;
    private final VpnServiceValidator validator;
    private final EventPublisher events;

    public VpnServiceServiceImpl(VpnServiceRepository repository, VpnServiceMapper mapper, VpnServiceValidator validator,
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

    private VpnService load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("VpnService", id));
    }

    @Override
    public VpnServiceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VpnServiceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("VpnService", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VpnServiceResponse> search(VpnServiceSearchCriteria criteria, Pageable pageable) {
        Page<VpnService> page = repository.findAll(VpnServiceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VpnServiceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VpnServiceResponse create(VpnServiceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("VpnService", "code", request.getCode());
        }
        VpnService entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        VpnService saved = repository.save(entity);
        VpnServiceResponse response = mapper.toResponse(saved);
        events.publish(VpnServiceEvents.created(response, actor()));
        log.info("Created VpnService id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VpnServiceResponse update(UUID id, VpnServiceUpdateRequest request) {
        VpnService entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "VpnService " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("VpnService", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        VpnService saved = repository.save(entity);
        VpnServiceResponse response = mapper.toResponse(saved);
        events.publish(VpnServiceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VpnServiceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VpnServiceResponse changeStatus(UUID id, LifecycleStatus status) {
        VpnService entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        VpnService saved = repository.save(entity);
        VpnServiceResponse response = mapper.toResponse(saved);
        events.publish(VpnServiceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        VpnService entity = load(id);
        VpnServiceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VpnServiceEvents.deleted(snapshot, actor()));
        log.info("Deleted VpnService id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VpnServiceResponse> bulkCreate(List<VpnServiceCreateRequest> requests) {
        BulkResult<VpnServiceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of VpnService failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VpnServiceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VpnServiceResponse> result = new BulkResult<>();
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
    public void exportCsv(VpnServiceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VpnServiceMapper.CSV_HEADER));
        int page = 0;
        Page<VpnService> chunk;
        do {
            chunk = repository.findAll(VpnServiceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<VpnServiceResponse> findByVrfId(UUID vrfId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndVrfId(tenant(), vrfId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<VpnServiceResponse> findByCustomerId(UUID customerId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCustomerId(tenant(), customerId, pageable).map(mapper::toResponse));
    }
}
