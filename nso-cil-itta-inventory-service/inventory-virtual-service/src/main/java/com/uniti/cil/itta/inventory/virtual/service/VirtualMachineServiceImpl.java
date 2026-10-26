package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.event.VirtualMachineEvents;
import com.uniti.cil.itta.inventory.virtual.mapper.VirtualMachineMapper;
import com.uniti.cil.itta.inventory.virtual.model.VirtualMachine;
import com.uniti.cil.itta.inventory.virtual.repository.VirtualMachineRepository;
import com.uniti.cil.itta.inventory.virtual.spec.VirtualMachineSpecifications;
import com.uniti.cil.itta.inventory.virtual.validation.VirtualMachineValidator;
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
public class VirtualMachineServiceImpl implements VirtualMachineService {

    private static final Logger log = LoggerFactory.getLogger(VirtualMachineServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final VirtualMachineRepository repository;
    private final VirtualMachineMapper mapper;
    private final VirtualMachineValidator validator;
    private final EventPublisher events;

    public VirtualMachineServiceImpl(VirtualMachineRepository repository, VirtualMachineMapper mapper, VirtualMachineValidator validator,
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

    private VirtualMachine load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("VirtualMachine", id));
    }

    @Override
    public VirtualMachineResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public VirtualMachineResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("VirtualMachine", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<VirtualMachineResponse> search(VirtualMachineSearchCriteria criteria, Pageable pageable) {
        Page<VirtualMachine> page = repository.findAll(VirtualMachineSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<VirtualMachineSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public VirtualMachineResponse create(VirtualMachineCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("VirtualMachine", "code", request.getCode());
        }
        VirtualMachine entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        VirtualMachine saved = repository.save(entity);
        VirtualMachineResponse response = mapper.toResponse(saved);
        events.publish(VirtualMachineEvents.created(response, actor()));
        log.info("Created VirtualMachine id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public VirtualMachineResponse update(UUID id, VirtualMachineUpdateRequest request) {
        VirtualMachine entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "VirtualMachine " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("VirtualMachine", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        VirtualMachine saved = repository.save(entity);
        VirtualMachineResponse response = mapper.toResponse(saved);
        events.publish(VirtualMachineEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(VirtualMachineEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public VirtualMachineResponse changeStatus(UUID id, LifecycleStatus status) {
        VirtualMachine entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        VirtualMachine saved = repository.save(entity);
        VirtualMachineResponse response = mapper.toResponse(saved);
        events.publish(VirtualMachineEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        VirtualMachine entity = load(id);
        VirtualMachineResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(VirtualMachineEvents.deleted(snapshot, actor()));
        log.info("Deleted VirtualMachine id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<VirtualMachineResponse> bulkCreate(List<VirtualMachineCreateRequest> requests) {
        BulkResult<VirtualMachineResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of VirtualMachine failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<VirtualMachineResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<VirtualMachineResponse> result = new BulkResult<>();
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
    public void exportCsv(VirtualMachineSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(VirtualMachineMapper.CSV_HEADER));
        int page = 0;
        Page<VirtualMachine> chunk;
        do {
            chunk = repository.findAll(VirtualMachineSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<VirtualMachineResponse> findByHypervisorId(UUID hypervisorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndHypervisorId(tenant(), hypervisorId, pageable).map(mapper::toResponse));
    }
}
