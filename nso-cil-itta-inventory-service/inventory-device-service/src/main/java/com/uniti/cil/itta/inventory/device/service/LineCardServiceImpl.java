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
import com.uniti.cil.itta.inventory.device.event.LineCardEvents;
import com.uniti.cil.itta.inventory.device.mapper.LineCardMapper;
import com.uniti.cil.itta.inventory.device.model.LineCard;
import com.uniti.cil.itta.inventory.device.repository.LineCardRepository;
import com.uniti.cil.itta.inventory.device.spec.LineCardSpecifications;
import com.uniti.cil.itta.inventory.device.validation.LineCardValidator;
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
public class LineCardServiceImpl implements LineCardService {

    private static final Logger log = LoggerFactory.getLogger(LineCardServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final LineCardRepository repository;
    private final LineCardMapper mapper;
    private final LineCardValidator validator;
    private final EventPublisher events;

    public LineCardServiceImpl(LineCardRepository repository, LineCardMapper mapper, LineCardValidator validator,
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

    private LineCard load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("LineCard", id));
    }

    @Override
    public LineCardResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public LineCardResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("LineCard", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<LineCardResponse> search(LineCardSearchCriteria criteria, Pageable pageable) {
        Page<LineCard> page = repository.findAll(LineCardSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<LineCardSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public LineCardResponse create(LineCardCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("LineCard", "code", request.getCode());
        }
        LineCard entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        LineCard saved = repository.save(entity);
        LineCardResponse response = mapper.toResponse(saved);
        events.publish(LineCardEvents.created(response, actor()));
        log.info("Created LineCard id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public LineCardResponse update(UUID id, LineCardUpdateRequest request) {
        LineCard entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "LineCard " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("LineCard", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        LineCard saved = repository.save(entity);
        LineCardResponse response = mapper.toResponse(saved);
        events.publish(LineCardEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(LineCardEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public LineCardResponse changeStatus(UUID id, LifecycleStatus status) {
        LineCard entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        LineCard saved = repository.save(entity);
        LineCardResponse response = mapper.toResponse(saved);
        events.publish(LineCardEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        LineCard entity = load(id);
        LineCardResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(LineCardEvents.deleted(snapshot, actor()));
        log.info("Deleted LineCard id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<LineCardResponse> bulkCreate(List<LineCardCreateRequest> requests) {
        BulkResult<LineCardResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of LineCard failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<LineCardResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<LineCardResponse> result = new BulkResult<>();
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
    public void exportCsv(LineCardSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(LineCardMapper.CSV_HEADER));
        int page = 0;
        Page<LineCard> chunk;
        do {
            chunk = repository.findAll(LineCardSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<LineCardResponse> findByChassisId(UUID chassisId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndChassisId(tenant(), chassisId, pageable).map(mapper::toResponse));
    }
}
