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
import com.uniti.cil.itta.inventory.device.event.PowerSupplyEvents;
import com.uniti.cil.itta.inventory.device.mapper.PowerSupplyMapper;
import com.uniti.cil.itta.inventory.device.model.PowerSupply;
import com.uniti.cil.itta.inventory.device.repository.PowerSupplyRepository;
import com.uniti.cil.itta.inventory.device.spec.PowerSupplySpecifications;
import com.uniti.cil.itta.inventory.device.validation.PowerSupplyValidator;
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
public class PowerSupplyServiceImpl implements PowerSupplyService {

    private static final Logger log = LoggerFactory.getLogger(PowerSupplyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final PowerSupplyRepository repository;
    private final PowerSupplyMapper mapper;
    private final PowerSupplyValidator validator;
    private final EventPublisher events;

    public PowerSupplyServiceImpl(PowerSupplyRepository repository, PowerSupplyMapper mapper, PowerSupplyValidator validator,
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

    private PowerSupply load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("PowerSupply", id));
    }

    @Override
    public PowerSupplyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public PowerSupplyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("PowerSupply", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<PowerSupplyResponse> search(PowerSupplySearchCriteria criteria, Pageable pageable) {
        Page<PowerSupply> page = repository.findAll(PowerSupplySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<PowerSupplySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public PowerSupplyResponse create(PowerSupplyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("PowerSupply", "code", request.getCode());
        }
        PowerSupply entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        PowerSupply saved = repository.save(entity);
        PowerSupplyResponse response = mapper.toResponse(saved);
        events.publish(PowerSupplyEvents.created(response, actor()));
        log.info("Created PowerSupply id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public PowerSupplyResponse update(UUID id, PowerSupplyUpdateRequest request) {
        PowerSupply entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "PowerSupply " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("PowerSupply", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        PowerSupply saved = repository.save(entity);
        PowerSupplyResponse response = mapper.toResponse(saved);
        events.publish(PowerSupplyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(PowerSupplyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public PowerSupplyResponse changeStatus(UUID id, LifecycleStatus status) {
        PowerSupply entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        PowerSupply saved = repository.save(entity);
        PowerSupplyResponse response = mapper.toResponse(saved);
        events.publish(PowerSupplyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        PowerSupply entity = load(id);
        PowerSupplyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(PowerSupplyEvents.deleted(snapshot, actor()));
        log.info("Deleted PowerSupply id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<PowerSupplyResponse> bulkCreate(List<PowerSupplyCreateRequest> requests) {
        BulkResult<PowerSupplyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of PowerSupply failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<PowerSupplyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<PowerSupplyResponse> result = new BulkResult<>();
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
    public void exportCsv(PowerSupplySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(PowerSupplyMapper.CSV_HEADER));
        int page = 0;
        Page<PowerSupply> chunk;
        do {
            chunk = repository.findAll(PowerSupplySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<PowerSupplyResponse> findByChassisId(UUID chassisId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndChassisId(tenant(), chassisId, pageable).map(mapper::toResponse));
    }

    @Override
    public PageResponse<PowerSupplyResponse> findByFeedId(UUID feedId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndFeedId(tenant(), feedId, pageable).map(mapper::toResponse));
    }
}
