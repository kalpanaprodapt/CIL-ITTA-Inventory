package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.event.WebhookSubscriptionEvents;
import com.uniti.cil.itta.inventory.integration.mapper.WebhookSubscriptionMapper;
import com.uniti.cil.itta.inventory.integration.model.WebhookSubscription;
import com.uniti.cil.itta.inventory.integration.repository.WebhookSubscriptionRepository;
import com.uniti.cil.itta.inventory.integration.spec.WebhookSubscriptionSpecifications;
import com.uniti.cil.itta.inventory.integration.validation.WebhookSubscriptionValidator;
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
public class WebhookSubscriptionServiceImpl implements WebhookSubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(WebhookSubscriptionServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final WebhookSubscriptionRepository repository;
    private final WebhookSubscriptionMapper mapper;
    private final WebhookSubscriptionValidator validator;
    private final EventPublisher events;

    public WebhookSubscriptionServiceImpl(WebhookSubscriptionRepository repository, WebhookSubscriptionMapper mapper, WebhookSubscriptionValidator validator,
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

    private WebhookSubscription load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("WebhookSubscription", id));
    }

    @Override
    public WebhookSubscriptionResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public WebhookSubscriptionResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("WebhookSubscription", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<WebhookSubscriptionResponse> search(WebhookSubscriptionSearchCriteria criteria, Pageable pageable) {
        Page<WebhookSubscription> page = repository.findAll(WebhookSubscriptionSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<WebhookSubscriptionSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public WebhookSubscriptionResponse create(WebhookSubscriptionCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("WebhookSubscription", "code", request.getCode());
        }
        WebhookSubscription entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        WebhookSubscription saved = repository.save(entity);
        WebhookSubscriptionResponse response = mapper.toResponse(saved);
        events.publish(WebhookSubscriptionEvents.created(response, actor()));
        log.info("Created WebhookSubscription id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public WebhookSubscriptionResponse update(UUID id, WebhookSubscriptionUpdateRequest request) {
        WebhookSubscription entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "WebhookSubscription " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("WebhookSubscription", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        WebhookSubscription saved = repository.save(entity);
        WebhookSubscriptionResponse response = mapper.toResponse(saved);
        events.publish(WebhookSubscriptionEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(WebhookSubscriptionEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public WebhookSubscriptionResponse changeStatus(UUID id, LifecycleStatus status) {
        WebhookSubscription entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        WebhookSubscription saved = repository.save(entity);
        WebhookSubscriptionResponse response = mapper.toResponse(saved);
        events.publish(WebhookSubscriptionEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        WebhookSubscription entity = load(id);
        WebhookSubscriptionResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(WebhookSubscriptionEvents.deleted(snapshot, actor()));
        log.info("Deleted WebhookSubscription id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<WebhookSubscriptionResponse> bulkCreate(List<WebhookSubscriptionCreateRequest> requests) {
        BulkResult<WebhookSubscriptionResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of WebhookSubscription failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<WebhookSubscriptionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<WebhookSubscriptionResponse> result = new BulkResult<>();
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
    public void exportCsv(WebhookSubscriptionSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(WebhookSubscriptionMapper.CSV_HEADER));
        int page = 0;
        Page<WebhookSubscription> chunk;
        do {
            chunk = repository.findAll(WebhookSubscriptionSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
