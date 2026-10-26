package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.event.CloudAccountEvents;
import com.uniti.cil.itta.inventory.virtual.mapper.CloudAccountMapper;
import com.uniti.cil.itta.inventory.virtual.model.CloudAccount;
import com.uniti.cil.itta.inventory.virtual.repository.CloudAccountRepository;
import com.uniti.cil.itta.inventory.virtual.spec.CloudAccountSpecifications;
import com.uniti.cil.itta.inventory.virtual.validation.CloudAccountValidator;
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
public class CloudAccountServiceImpl implements CloudAccountService {

    private static final Logger log = LoggerFactory.getLogger(CloudAccountServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CloudAccountRepository repository;
    private final CloudAccountMapper mapper;
    private final CloudAccountValidator validator;
    private final EventPublisher events;

    public CloudAccountServiceImpl(CloudAccountRepository repository, CloudAccountMapper mapper, CloudAccountValidator validator,
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

    private CloudAccount load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("CloudAccount", id));
    }

    @Override
    public CloudAccountResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CloudAccountResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("CloudAccount", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CloudAccountResponse> search(CloudAccountSearchCriteria criteria, Pageable pageable) {
        Page<CloudAccount> page = repository.findAll(CloudAccountSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CloudAccountSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CloudAccountResponse create(CloudAccountCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("CloudAccount", "code", request.getCode());
        }
        CloudAccount entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        CloudAccount saved = repository.save(entity);
        CloudAccountResponse response = mapper.toResponse(saved);
        events.publish(CloudAccountEvents.created(response, actor()));
        log.info("Created CloudAccount id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CloudAccountResponse update(UUID id, CloudAccountUpdateRequest request) {
        CloudAccount entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "CloudAccount " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("CloudAccount", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        CloudAccount saved = repository.save(entity);
        CloudAccountResponse response = mapper.toResponse(saved);
        events.publish(CloudAccountEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CloudAccountEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CloudAccountResponse changeStatus(UUID id, LifecycleStatus status) {
        CloudAccount entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        CloudAccount saved = repository.save(entity);
        CloudAccountResponse response = mapper.toResponse(saved);
        events.publish(CloudAccountEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CloudAccount entity = load(id);
        CloudAccountResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CloudAccountEvents.deleted(snapshot, actor()));
        log.info("Deleted CloudAccount id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CloudAccountResponse> bulkCreate(List<CloudAccountCreateRequest> requests) {
        BulkResult<CloudAccountResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of CloudAccount failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CloudAccountResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CloudAccountResponse> result = new BulkResult<>();
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
    public void exportCsv(CloudAccountSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CloudAccountMapper.CSV_HEADER));
        int page = 0;
        Page<CloudAccount> chunk;
        do {
            chunk = repository.findAll(CloudAccountSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

}
