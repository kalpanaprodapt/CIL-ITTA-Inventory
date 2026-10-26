package com.uniti.cil.itta.inventory.device.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.event.NetworkDeviceEvents;
import com.uniti.cil.itta.inventory.device.mapper.NetworkDeviceMapper;
import com.uniti.cil.itta.inventory.device.model.NetworkDevice;
import com.uniti.cil.itta.inventory.device.repository.NetworkDeviceRepository;
import com.uniti.cil.itta.inventory.device.spec.NetworkDeviceSpecifications;
import com.uniti.cil.itta.inventory.device.validation.NetworkDeviceValidator;
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
public class NetworkDeviceServiceImpl implements NetworkDeviceService {

    private static final Logger log = LoggerFactory.getLogger(NetworkDeviceServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final NetworkDeviceRepository repository;
    private final NetworkDeviceMapper mapper;
    private final NetworkDeviceValidator validator;
    private final EventPublisher events;

    public NetworkDeviceServiceImpl(NetworkDeviceRepository repository, NetworkDeviceMapper mapper, NetworkDeviceValidator validator,
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

    private NetworkDevice load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("NetworkDevice", id));
    }

    @Override
    public NetworkDeviceResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public NetworkDeviceResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("NetworkDevice", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<NetworkDeviceResponse> search(NetworkDeviceSearchCriteria criteria, Pageable pageable) {
        Page<NetworkDevice> page = repository.findAll(NetworkDeviceSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<NetworkDeviceSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public NetworkDeviceResponse create(NetworkDeviceCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("NetworkDevice", "code", request.getCode());
        }
        NetworkDevice entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        NetworkDevice saved = repository.save(entity);
        NetworkDeviceResponse response = mapper.toResponse(saved);
        events.publish(NetworkDeviceEvents.created(response, actor()));
        log.info("Created NetworkDevice id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public NetworkDeviceResponse update(UUID id, NetworkDeviceUpdateRequest request) {
        NetworkDevice entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "NetworkDevice " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("NetworkDevice", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        NetworkDevice saved = repository.save(entity);
        NetworkDeviceResponse response = mapper.toResponse(saved);
        events.publish(NetworkDeviceEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(NetworkDeviceEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public NetworkDeviceResponse changeStatus(UUID id, LifecycleStatus status) {
        NetworkDevice entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        NetworkDevice saved = repository.save(entity);
        NetworkDeviceResponse response = mapper.toResponse(saved);
        events.publish(NetworkDeviceEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        NetworkDevice entity = load(id);
        NetworkDeviceResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(NetworkDeviceEvents.deleted(snapshot, actor()));
        log.info("Deleted NetworkDevice id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<NetworkDeviceResponse> bulkCreate(List<NetworkDeviceCreateRequest> requests) {
        BulkResult<NetworkDeviceResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of NetworkDevice failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<NetworkDeviceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<NetworkDeviceResponse> result = new BulkResult<>();
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
    public void exportCsv(NetworkDeviceSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(NetworkDeviceMapper.CSV_HEADER));
        int page = 0;
        Page<NetworkDevice> chunk;
        do {
            chunk = repository.findAll(NetworkDeviceSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<NetworkDeviceResponse> findByModelId(UUID modelId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndModelId(tenant(), modelId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<NetworkDeviceResponse> findByRackUnitId(UUID rackUnitId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndRackUnitId(tenant(), rackUnitId, pageable).map(mapper::toResponse));
    }
}
