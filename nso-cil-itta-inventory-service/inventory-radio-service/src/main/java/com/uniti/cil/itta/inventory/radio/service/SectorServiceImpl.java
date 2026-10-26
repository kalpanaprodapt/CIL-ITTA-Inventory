package com.uniti.cil.itta.inventory.radio.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.event.SectorEvents;
import com.uniti.cil.itta.inventory.radio.mapper.SectorMapper;
import com.uniti.cil.itta.inventory.radio.model.Sector;
import com.uniti.cil.itta.inventory.radio.repository.SectorRepository;
import com.uniti.cil.itta.inventory.radio.spec.SectorSpecifications;
import com.uniti.cil.itta.inventory.radio.validation.SectorValidator;
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
public class SectorServiceImpl implements SectorService {

    private static final Logger log = LoggerFactory.getLogger(SectorServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final SectorRepository repository;
    private final SectorMapper mapper;
    private final SectorValidator validator;
    private final EventPublisher events;

    public SectorServiceImpl(SectorRepository repository, SectorMapper mapper, SectorValidator validator,
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

    private Sector load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector", id));
    }

    @Override
    public SectorResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public SectorResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Sector", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<SectorResponse> search(SectorSearchCriteria criteria, Pageable pageable) {
        Page<Sector> page = repository.findAll(SectorSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<SectorSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public SectorResponse create(SectorCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Sector", "code", request.getCode());
        }
        Sector entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Sector saved = repository.save(entity);
        SectorResponse response = mapper.toResponse(saved);
        events.publish(SectorEvents.created(response, actor()));
        log.info("Created Sector id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public SectorResponse update(UUID id, SectorUpdateRequest request) {
        Sector entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Sector " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Sector", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Sector saved = repository.save(entity);
        SectorResponse response = mapper.toResponse(saved);
        events.publish(SectorEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(SectorEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public SectorResponse changeStatus(UUID id, LifecycleStatus status) {
        Sector entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Sector saved = repository.save(entity);
        SectorResponse response = mapper.toResponse(saved);
        events.publish(SectorEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Sector entity = load(id);
        SectorResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(SectorEvents.deleted(snapshot, actor()));
        log.info("Deleted Sector id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<SectorResponse> bulkCreate(List<SectorCreateRequest> requests) {
        BulkResult<SectorResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Sector failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<SectorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<SectorResponse> result = new BulkResult<>();
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
    public void exportCsv(SectorSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(SectorMapper.CSV_HEADER));
        int page = 0;
        Page<Sector> chunk;
        do {
            chunk = repository.findAll(SectorSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<SectorResponse> findByBaseStationId(UUID baseStationId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndBaseStationId(tenant(), baseStationId, pageable).map(mapper::toResponse));
    }
}
