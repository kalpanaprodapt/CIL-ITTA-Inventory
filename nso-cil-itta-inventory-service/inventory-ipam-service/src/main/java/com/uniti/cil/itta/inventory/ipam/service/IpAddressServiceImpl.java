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
import com.uniti.cil.itta.inventory.ipam.event.IpAddressEvents;
import com.uniti.cil.itta.inventory.ipam.mapper.IpAddressMapper;
import com.uniti.cil.itta.inventory.ipam.model.IpAddress;
import com.uniti.cil.itta.inventory.ipam.repository.IpAddressRepository;
import com.uniti.cil.itta.inventory.ipam.spec.IpAddressSpecifications;
import com.uniti.cil.itta.inventory.ipam.validation.IpAddressValidator;
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
public class IpAddressServiceImpl implements IpAddressService {

    private static final Logger log = LoggerFactory.getLogger(IpAddressServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final IpAddressRepository repository;
    private final IpAddressMapper mapper;
    private final IpAddressValidator validator;
    private final EventPublisher events;

    public IpAddressServiceImpl(IpAddressRepository repository, IpAddressMapper mapper, IpAddressValidator validator,
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

    private IpAddress load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("IpAddress", id));
    }

    @Override
    public IpAddressResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public IpAddressResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("IpAddress", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<IpAddressResponse> search(IpAddressSearchCriteria criteria, Pageable pageable) {
        Page<IpAddress> page = repository.findAll(IpAddressSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<IpAddressSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public IpAddressResponse create(IpAddressCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("IpAddress", "code", request.getCode());
        }
        IpAddress entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        IpAddress saved = repository.save(entity);
        IpAddressResponse response = mapper.toResponse(saved);
        events.publish(IpAddressEvents.created(response, actor()));
        log.info("Created IpAddress id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public IpAddressResponse update(UUID id, IpAddressUpdateRequest request) {
        IpAddress entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "IpAddress " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("IpAddress", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        IpAddress saved = repository.save(entity);
        IpAddressResponse response = mapper.toResponse(saved);
        events.publish(IpAddressEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(IpAddressEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public IpAddressResponse changeStatus(UUID id, LifecycleStatus status) {
        IpAddress entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        IpAddress saved = repository.save(entity);
        IpAddressResponse response = mapper.toResponse(saved);
        events.publish(IpAddressEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        IpAddress entity = load(id);
        IpAddressResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(IpAddressEvents.deleted(snapshot, actor()));
        log.info("Deleted IpAddress id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<IpAddressResponse> bulkCreate(List<IpAddressCreateRequest> requests) {
        BulkResult<IpAddressResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of IpAddress failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<IpAddressResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<IpAddressResponse> result = new BulkResult<>();
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
    public void exportCsv(IpAddressSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(IpAddressMapper.CSV_HEADER));
        int page = 0;
        Page<IpAddress> chunk;
        do {
            chunk = repository.findAll(IpAddressSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<IpAddressResponse> findBySubnetId(UUID subnetId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSubnetId(tenant(), subnetId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<IpAddressResponse> findByAssignedToId(UUID assignedToId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndAssignedToId(tenant(), assignedToId, pageable).map(mapper::toResponse));
    }
}
