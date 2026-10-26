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
import com.uniti.cil.itta.inventory.ipam.event.DnsRecordEvents;
import com.uniti.cil.itta.inventory.ipam.mapper.DnsRecordMapper;
import com.uniti.cil.itta.inventory.ipam.model.DnsRecord;
import com.uniti.cil.itta.inventory.ipam.repository.DnsRecordRepository;
import com.uniti.cil.itta.inventory.ipam.spec.DnsRecordSpecifications;
import com.uniti.cil.itta.inventory.ipam.validation.DnsRecordValidator;
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
public class DnsRecordServiceImpl implements DnsRecordService {

    private static final Logger log = LoggerFactory.getLogger(DnsRecordServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final DnsRecordRepository repository;
    private final DnsRecordMapper mapper;
    private final DnsRecordValidator validator;
    private final EventPublisher events;

    public DnsRecordServiceImpl(DnsRecordRepository repository, DnsRecordMapper mapper, DnsRecordValidator validator,
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

    private DnsRecord load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("DnsRecord", id));
    }

    @Override
    public DnsRecordResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public DnsRecordResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DnsRecord", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<DnsRecordResponse> search(DnsRecordSearchCriteria criteria, Pageable pageable) {
        Page<DnsRecord> page = repository.findAll(DnsRecordSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<DnsRecordSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public DnsRecordResponse create(DnsRecordCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("DnsRecord", "code", request.getCode());
        }
        DnsRecord entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        DnsRecord saved = repository.save(entity);
        DnsRecordResponse response = mapper.toResponse(saved);
        events.publish(DnsRecordEvents.created(response, actor()));
        log.info("Created DnsRecord id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public DnsRecordResponse update(UUID id, DnsRecordUpdateRequest request) {
        DnsRecord entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "DnsRecord " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("DnsRecord", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        DnsRecord saved = repository.save(entity);
        DnsRecordResponse response = mapper.toResponse(saved);
        events.publish(DnsRecordEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(DnsRecordEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public DnsRecordResponse changeStatus(UUID id, LifecycleStatus status) {
        DnsRecord entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        DnsRecord saved = repository.save(entity);
        DnsRecordResponse response = mapper.toResponse(saved);
        events.publish(DnsRecordEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DnsRecord entity = load(id);
        DnsRecordResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(DnsRecordEvents.deleted(snapshot, actor()));
        log.info("Deleted DnsRecord id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<DnsRecordResponse> bulkCreate(List<DnsRecordCreateRequest> requests) {
        BulkResult<DnsRecordResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of DnsRecord failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<DnsRecordResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<DnsRecordResponse> result = new BulkResult<>();
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
    public void exportCsv(DnsRecordSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(DnsRecordMapper.CSV_HEADER));
        int page = 0;
        Page<DnsRecord> chunk;
        do {
            chunk = repository.findAll(DnsRecordSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
