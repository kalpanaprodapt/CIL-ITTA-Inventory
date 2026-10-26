package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.event.TransceiverEvents;
import com.uniti.cil.itta.inventory.port.mapper.TransceiverMapper;
import com.uniti.cil.itta.inventory.port.model.Transceiver;
import com.uniti.cil.itta.inventory.port.repository.TransceiverRepository;
import com.uniti.cil.itta.inventory.port.spec.TransceiverSpecifications;
import com.uniti.cil.itta.inventory.port.validation.TransceiverValidator;
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
public class TransceiverServiceImpl implements TransceiverService {

    private static final Logger log = LoggerFactory.getLogger(TransceiverServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final TransceiverRepository repository;
    private final TransceiverMapper mapper;
    private final TransceiverValidator validator;
    private final EventPublisher events;

    public TransceiverServiceImpl(TransceiverRepository repository, TransceiverMapper mapper, TransceiverValidator validator,
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

    private Transceiver load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Transceiver", id));
    }

    @Override
    public TransceiverResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public TransceiverResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Transceiver", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<TransceiverResponse> search(TransceiverSearchCriteria criteria, Pageable pageable) {
        Page<Transceiver> page = repository.findAll(TransceiverSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<TransceiverSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public TransceiverResponse create(TransceiverCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Transceiver", "code", request.getCode());
        }
        Transceiver entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Transceiver saved = repository.save(entity);
        TransceiverResponse response = mapper.toResponse(saved);
        events.publish(TransceiverEvents.created(response, actor()));
        log.info("Created Transceiver id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public TransceiverResponse update(UUID id, TransceiverUpdateRequest request) {
        Transceiver entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Transceiver " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Transceiver", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Transceiver saved = repository.save(entity);
        TransceiverResponse response = mapper.toResponse(saved);
        events.publish(TransceiverEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(TransceiverEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public TransceiverResponse changeStatus(UUID id, LifecycleStatus status) {
        Transceiver entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Transceiver saved = repository.save(entity);
        TransceiverResponse response = mapper.toResponse(saved);
        events.publish(TransceiverEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Transceiver entity = load(id);
        TransceiverResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(TransceiverEvents.deleted(snapshot, actor()));
        log.info("Deleted Transceiver id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<TransceiverResponse> bulkCreate(List<TransceiverCreateRequest> requests) {
        BulkResult<TransceiverResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Transceiver failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<TransceiverResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<TransceiverResponse> result = new BulkResult<>();
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
    public void exportCsv(TransceiverSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(TransceiverMapper.CSV_HEADER));
        int page = 0;
        Page<Transceiver> chunk;
        do {
            chunk = repository.findAll(TransceiverSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<TransceiverResponse> findByPortId(UUID portId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndPortId(tenant(), portId, pageable).map(mapper::toResponse));
    }
}
