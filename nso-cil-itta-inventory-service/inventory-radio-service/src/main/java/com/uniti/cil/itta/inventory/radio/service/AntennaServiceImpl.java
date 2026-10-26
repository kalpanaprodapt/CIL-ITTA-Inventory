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
import com.uniti.cil.itta.inventory.radio.event.AntennaEvents;
import com.uniti.cil.itta.inventory.radio.mapper.AntennaMapper;
import com.uniti.cil.itta.inventory.radio.model.Antenna;
import com.uniti.cil.itta.inventory.radio.repository.AntennaRepository;
import com.uniti.cil.itta.inventory.radio.spec.AntennaSpecifications;
import com.uniti.cil.itta.inventory.radio.validation.AntennaValidator;
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
public class AntennaServiceImpl implements AntennaService {

    private static final Logger log = LoggerFactory.getLogger(AntennaServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AntennaRepository repository;
    private final AntennaMapper mapper;
    private final AntennaValidator validator;
    private final EventPublisher events;

    public AntennaServiceImpl(AntennaRepository repository, AntennaMapper mapper, AntennaValidator validator,
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

    private Antenna load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Antenna", id));
    }

    @Override
    public AntennaResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AntennaResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Antenna", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AntennaResponse> search(AntennaSearchCriteria criteria, Pageable pageable) {
        Page<Antenna> page = repository.findAll(AntennaSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AntennaSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AntennaResponse create(AntennaCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Antenna", "code", request.getCode());
        }
        Antenna entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Antenna saved = repository.save(entity);
        AntennaResponse response = mapper.toResponse(saved);
        events.publish(AntennaEvents.created(response, actor()));
        log.info("Created Antenna id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AntennaResponse update(UUID id, AntennaUpdateRequest request) {
        Antenna entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Antenna " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Antenna", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Antenna saved = repository.save(entity);
        AntennaResponse response = mapper.toResponse(saved);
        events.publish(AntennaEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AntennaEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AntennaResponse changeStatus(UUID id, LifecycleStatus status) {
        Antenna entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Antenna saved = repository.save(entity);
        AntennaResponse response = mapper.toResponse(saved);
        events.publish(AntennaEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Antenna entity = load(id);
        AntennaResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AntennaEvents.deleted(snapshot, actor()));
        log.info("Deleted Antenna id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AntennaResponse> bulkCreate(List<AntennaCreateRequest> requests) {
        BulkResult<AntennaResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Antenna failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AntennaResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AntennaResponse> result = new BulkResult<>();
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
    public void exportCsv(AntennaSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AntennaMapper.CSV_HEADER));
        int page = 0;
        Page<Antenna> chunk;
        do {
            chunk = repository.findAll(AntennaSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<AntennaResponse> findBySectorId(UUID sectorId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSectorId(tenant(), sectorId, pageable).map(mapper::toResponse));
    }
}
