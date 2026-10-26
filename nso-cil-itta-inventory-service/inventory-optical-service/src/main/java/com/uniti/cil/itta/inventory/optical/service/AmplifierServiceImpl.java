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
import com.uniti.cil.itta.inventory.optical.event.AmplifierEvents;
import com.uniti.cil.itta.inventory.optical.mapper.AmplifierMapper;
import com.uniti.cil.itta.inventory.optical.model.Amplifier;
import com.uniti.cil.itta.inventory.optical.repository.AmplifierRepository;
import com.uniti.cil.itta.inventory.optical.spec.AmplifierSpecifications;
import com.uniti.cil.itta.inventory.optical.validation.AmplifierValidator;
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
public class AmplifierServiceImpl implements AmplifierService {

    private static final Logger log = LoggerFactory.getLogger(AmplifierServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final AmplifierRepository repository;
    private final AmplifierMapper mapper;
    private final AmplifierValidator validator;
    private final EventPublisher events;

    public AmplifierServiceImpl(AmplifierRepository repository, AmplifierMapper mapper, AmplifierValidator validator,
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

    private Amplifier load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Amplifier", id));
    }

    @Override
    public AmplifierResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public AmplifierResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Amplifier", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<AmplifierResponse> search(AmplifierSearchCriteria criteria, Pageable pageable) {
        Page<Amplifier> page = repository.findAll(AmplifierSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<AmplifierSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public AmplifierResponse create(AmplifierCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("Amplifier", "code", request.getCode());
        }
        Amplifier entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        Amplifier saved = repository.save(entity);
        AmplifierResponse response = mapper.toResponse(saved);
        events.publish(AmplifierEvents.created(response, actor()));
        log.info("Created Amplifier id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public AmplifierResponse update(UUID id, AmplifierUpdateRequest request) {
        Amplifier entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "Amplifier " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("Amplifier", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        Amplifier saved = repository.save(entity);
        AmplifierResponse response = mapper.toResponse(saved);
        events.publish(AmplifierEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(AmplifierEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public AmplifierResponse changeStatus(UUID id, LifecycleStatus status) {
        Amplifier entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        Amplifier saved = repository.save(entity);
        AmplifierResponse response = mapper.toResponse(saved);
        events.publish(AmplifierEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Amplifier entity = load(id);
        AmplifierResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(AmplifierEvents.deleted(snapshot, actor()));
        log.info("Deleted Amplifier id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<AmplifierResponse> bulkCreate(List<AmplifierCreateRequest> requests) {
        BulkResult<AmplifierResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of Amplifier failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<AmplifierResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<AmplifierResponse> result = new BulkResult<>();
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
    public void exportCsv(AmplifierSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(AmplifierMapper.CSV_HEADER));
        int page = 0;
        Page<Amplifier> chunk;
        do {
            chunk = repository.findAll(AmplifierSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<AmplifierResponse> findBySystemId(UUID systemId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndSystemId(tenant(), systemId, pageable).map(mapper::toResponse));
    }
}
