package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.event.OpticalChannelEvents;
import com.uniti.cil.itta.inventory.optical.mapper.OpticalChannelMapper;
import com.uniti.cil.itta.inventory.optical.model.OpticalChannel;
import com.uniti.cil.itta.inventory.optical.repository.OpticalChannelRepository;
import com.uniti.cil.itta.inventory.optical.spec.OpticalChannelSpecifications;
import com.uniti.cil.itta.inventory.optical.validation.OpticalChannelValidator;
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
public class OpticalChannelServiceImpl implements OpticalChannelService {

    private static final Logger log = LoggerFactory.getLogger(OpticalChannelServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final OpticalChannelRepository repository;
    private final OpticalChannelMapper mapper;
    private final OpticalChannelValidator validator;
    private final EventPublisher events;

    public OpticalChannelServiceImpl(OpticalChannelRepository repository, OpticalChannelMapper mapper, OpticalChannelValidator validator,
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

    private OpticalChannel load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("OpticalChannel", id));
    }

    @Override
    public OpticalChannelResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public OpticalChannelResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("OpticalChannel", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<OpticalChannelResponse> search(OpticalChannelSearchCriteria criteria, Pageable pageable) {
        Page<OpticalChannel> page = repository.findAll(OpticalChannelSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<OpticalChannelSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public OpticalChannelResponse create(OpticalChannelCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("OpticalChannel", "code", request.getCode());
        }
        OpticalChannel entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        OpticalChannel saved = repository.save(entity);
        OpticalChannelResponse response = mapper.toResponse(saved);
        events.publish(OpticalChannelEvents.created(response, actor()));
        log.info("Created OpticalChannel id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public OpticalChannelResponse update(UUID id, OpticalChannelUpdateRequest request) {
        OpticalChannel entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "OpticalChannel " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("OpticalChannel", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        OpticalChannel saved = repository.save(entity);
        OpticalChannelResponse response = mapper.toResponse(saved);
        events.publish(OpticalChannelEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(OpticalChannelEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public OpticalChannelResponse changeStatus(UUID id, LifecycleStatus status) {
        OpticalChannel entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        OpticalChannel saved = repository.save(entity);
        OpticalChannelResponse response = mapper.toResponse(saved);
        events.publish(OpticalChannelEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        OpticalChannel entity = load(id);
        OpticalChannelResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(OpticalChannelEvents.deleted(snapshot, actor()));
        log.info("Deleted OpticalChannel id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<OpticalChannelResponse> bulkCreate(List<OpticalChannelCreateRequest> requests) {
        BulkResult<OpticalChannelResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of OpticalChannel failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<OpticalChannelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<OpticalChannelResponse> result = new BulkResult<>();
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
    public void exportCsv(OpticalChannelSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(OpticalChannelMapper.CSV_HEADER));
        int page = 0;
        Page<OpticalChannel> chunk;
        do {
            chunk = repository.findAll(OpticalChannelSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<OpticalChannelResponse> findBySystemId(UUID systemId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSystemId(tenant(), systemId, pageable).map(mapper::toResponse));
    }
}
