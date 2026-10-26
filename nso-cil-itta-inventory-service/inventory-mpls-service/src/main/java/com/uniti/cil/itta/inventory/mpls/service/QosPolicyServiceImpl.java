package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.event.QosPolicyEvents;
import com.uniti.cil.itta.inventory.mpls.mapper.QosPolicyMapper;
import com.uniti.cil.itta.inventory.mpls.model.QosPolicy;
import com.uniti.cil.itta.inventory.mpls.repository.QosPolicyRepository;
import com.uniti.cil.itta.inventory.mpls.spec.QosPolicySpecifications;
import com.uniti.cil.itta.inventory.mpls.validation.QosPolicyValidator;
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
public class QosPolicyServiceImpl implements QosPolicyService {

    private static final Logger log = LoggerFactory.getLogger(QosPolicyServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final QosPolicyRepository repository;
    private final QosPolicyMapper mapper;
    private final QosPolicyValidator validator;
    private final EventPublisher events;

    public QosPolicyServiceImpl(QosPolicyRepository repository, QosPolicyMapper mapper, QosPolicyValidator validator,
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

    private QosPolicy load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("QosPolicy", id));
    }

    @Override
    public QosPolicyResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public QosPolicyResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("QosPolicy", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<QosPolicyResponse> search(QosPolicySearchCriteria criteria, Pageable pageable) {
        Page<QosPolicy> page = repository.findAll(QosPolicySpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<QosPolicySummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public QosPolicyResponse create(QosPolicyCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("QosPolicy", "code", request.getCode());
        }
        QosPolicy entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        QosPolicy saved = repository.save(entity);
        QosPolicyResponse response = mapper.toResponse(saved);
        events.publish(QosPolicyEvents.created(response, actor()));
        log.info("Created QosPolicy id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public QosPolicyResponse update(UUID id, QosPolicyUpdateRequest request) {
        QosPolicy entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "QosPolicy " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("QosPolicy", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        QosPolicy saved = repository.save(entity);
        QosPolicyResponse response = mapper.toResponse(saved);
        events.publish(QosPolicyEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(QosPolicyEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public QosPolicyResponse changeStatus(UUID id, LifecycleStatus status) {
        QosPolicy entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        QosPolicy saved = repository.save(entity);
        QosPolicyResponse response = mapper.toResponse(saved);
        events.publish(QosPolicyEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        QosPolicy entity = load(id);
        QosPolicyResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(QosPolicyEvents.deleted(snapshot, actor()));
        log.info("Deleted QosPolicy id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<QosPolicyResponse> bulkCreate(List<QosPolicyCreateRequest> requests) {
        BulkResult<QosPolicyResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of QosPolicy failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<QosPolicyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<QosPolicyResponse> result = new BulkResult<>();
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
    public void exportCsv(QosPolicySearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(QosPolicyMapper.CSV_HEADER));
        int page = 0;
        Page<QosPolicy> chunk;
        do {
            chunk = repository.findAll(QosPolicySpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
