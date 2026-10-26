package com.uniti.cil.itta.inventory.ipam.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.event.IpPoolEvents;
import com.uniti.cil.itta.inventory.ipam.mapper.IpPoolMapper;
import com.uniti.cil.itta.inventory.ipam.model.IpPool;
import com.uniti.cil.itta.inventory.ipam.repository.IpPoolRepository;
import com.uniti.cil.itta.inventory.ipam.spec.IpPoolSpecifications;
import com.uniti.cil.itta.inventory.ipam.validation.IpPoolValidator;
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
public class IpPoolServiceImpl implements IpPoolService {

    private static final Logger log = LoggerFactory.getLogger(IpPoolServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final IpPoolRepository repository;
    private final IpPoolMapper mapper;
    private final IpPoolValidator validator;
    private final EventPublisher events;

    public IpPoolServiceImpl(IpPoolRepository repository, IpPoolMapper mapper, IpPoolValidator validator,
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

    private IpPool load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("IpPool", id));
    }

    @Override
    public IpPoolResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public IpPoolResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("IpPool", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<IpPoolResponse> search(IpPoolSearchCriteria criteria, Pageable pageable) {
        Page<IpPool> page = repository.findAll(IpPoolSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<IpPoolSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public IpPoolResponse create(IpPoolCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("IpPool", "code", request.getCode());
        }
        IpPool entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        IpPool saved = repository.save(entity);
        IpPoolResponse response = mapper.toResponse(saved);
        events.publish(IpPoolEvents.created(response, actor()));
        log.info("Created IpPool id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public IpPoolResponse update(UUID id, IpPoolUpdateRequest request) {
        IpPool entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "IpPool " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("IpPool", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        IpPool saved = repository.save(entity);
        IpPoolResponse response = mapper.toResponse(saved);
        events.publish(IpPoolEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(IpPoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public IpPoolResponse changeStatus(UUID id, LifecycleStatus status) {
        IpPool entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        IpPool saved = repository.save(entity);
        IpPoolResponse response = mapper.toResponse(saved);
        events.publish(IpPoolEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        IpPool entity = load(id);
        IpPoolResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(IpPoolEvents.deleted(snapshot, actor()));
        log.info("Deleted IpPool id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<IpPoolResponse> bulkCreate(List<IpPoolCreateRequest> requests) {
        BulkResult<IpPoolResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of IpPool failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<IpPoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<IpPoolResponse> result = new BulkResult<>();
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
    public void exportCsv(IpPoolSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(IpPoolMapper.CSV_HEADER));
        int page = 0;
        Page<IpPool> chunk;
        do {
            chunk = repository.findAll(IpPoolSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<IpPoolResponse> findBySubnetId(UUID subnetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSubnetId(tenant(), subnetId, pageable).map(mapper::toResponse));
    }
}
