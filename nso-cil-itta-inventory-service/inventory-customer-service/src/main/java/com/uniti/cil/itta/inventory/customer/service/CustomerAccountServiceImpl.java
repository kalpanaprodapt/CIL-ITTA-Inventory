package com.uniti.cil.itta.inventory.customer.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.event.EventPublisher;
import com.uniti.cil.itta.inventory.common.exception.DuplicateResourceException;
import com.uniti.cil.itta.inventory.common.exception.ResourceNotFoundException;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.common.security.TenantContext;
import com.uniti.cil.itta.inventory.common.util.CsvWriter;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.event.CustomerAccountEvents;
import com.uniti.cil.itta.inventory.customer.mapper.CustomerAccountMapper;
import com.uniti.cil.itta.inventory.customer.model.CustomerAccount;
import com.uniti.cil.itta.inventory.customer.repository.CustomerAccountRepository;
import com.uniti.cil.itta.inventory.customer.spec.CustomerAccountSpecifications;
import com.uniti.cil.itta.inventory.customer.validation.CustomerAccountValidator;
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
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private static final Logger log = LoggerFactory.getLogger(CustomerAccountServiceImpl.class);
    private static final int EXPORT_PAGE_SIZE = 500;

    private final CustomerAccountRepository repository;
    private final CustomerAccountMapper mapper;
    private final CustomerAccountValidator validator;
    private final EventPublisher events;

    public CustomerAccountServiceImpl(CustomerAccountRepository repository, CustomerAccountMapper mapper, CustomerAccountValidator validator,
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

    private CustomerAccount load(UUID id) {
        return repository.findByTenantIdAndId(tenant(), id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerAccount", id));
    }

    @Override
    public CustomerAccountResponse get(UUID id) {
        return mapper.toResponse(load(id));
    }

    @Override
    public CustomerAccountResponse getByCode(String code) {
        return repository.findByTenantIdAndCode(tenant(), code)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerAccount", code));
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByTenantIdAndCode(tenant(), code);
    }

    @Override
    public PageResponse<CustomerAccountResponse> search(CustomerAccountSearchCriteria criteria, Pageable pageable) {
        Page<CustomerAccount> page = repository.findAll(CustomerAccountSpecifications.fromCriteria(tenant(), criteria), pageable);
        return PageResponse.from(page.map(mapper::toResponse));
    }

    @Override
    public List<CustomerAccountSummary> summaries(LifecycleStatus status) {
        LifecycleStatus wanted = status == null ? LifecycleStatus.ACTIVE : status;
        return repository.findAllByStatusOrdered(tenant(), wanted).stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional
    public CustomerAccountResponse create(CustomerAccountCreateRequest request) {
        if (repository.existsByTenantIdAndCode(tenant(), request.getCode())) {
            throw new DuplicateResourceException("CustomerAccount", "code", request.getCode());
        }
        CustomerAccount entity = mapper.toEntity(request);
        entity.setTenantId(tenant());
        validator.validate(entity);
        CustomerAccount saved = repository.save(entity);
        CustomerAccountResponse response = mapper.toResponse(saved);
        events.publish(CustomerAccountEvents.created(response, actor()));
        log.info("Created CustomerAccount id={} code={}", saved.getId(), saved.getCode());
        return response;
    }

    @Override
    @Transactional
    public CustomerAccountResponse update(UUID id, CustomerAccountUpdateRequest request) {
        CustomerAccount entity = load(id);
        if (request.getExpectedVersion() != null && !request.getExpectedVersion().equals(entity.getVersion())) {
            throw new org.springframework.dao.OptimisticLockingFailureException(
                    "CustomerAccount " + id + " was modified by another user");
        }
        if (request.getCode() != null && !request.getCode().equals(entity.getCode())
                && repository.existsByTenantIdAndCodeAndIdNot(tenant(), request.getCode(), id)) {
            throw new DuplicateResourceException("CustomerAccount", "code", request.getCode());
        }
        LifecycleStatus previous = entity.getStatus();
        mapper.applyUpdate(request, entity);
        validator.validate(entity);
        CustomerAccount saved = repository.save(entity);
        CustomerAccountResponse response = mapper.toResponse(saved);
        events.publish(CustomerAccountEvents.updated(response, actor()));
        if (previous != saved.getStatus()) {
            events.publish(CustomerAccountEvents.statusChanged(response, String.valueOf(previous), actor()));
        }
        return response;
    }

    @Override
    @Transactional
    public CustomerAccountResponse changeStatus(UUID id, LifecycleStatus status) {
        CustomerAccount entity = load(id);
        LifecycleStatus previous = entity.getStatus();
        if (previous == status) {
            return mapper.toResponse(entity);
        }
        entity.setStatus(status);
        CustomerAccount saved = repository.save(entity);
        CustomerAccountResponse response = mapper.toResponse(saved);
        events.publish(CustomerAccountEvents.statusChanged(response, String.valueOf(previous), actor()));
        return response;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CustomerAccount entity = load(id);
        CustomerAccountResponse snapshot = mapper.toResponse(entity);
        repository.delete(entity);
        events.publish(CustomerAccountEvents.deleted(snapshot, actor()));
        log.info("Deleted CustomerAccount id={}", id);
    }

    @Override
    @Transactional
    public BulkResult<CustomerAccountResponse> bulkCreate(List<CustomerAccountCreateRequest> requests) {
        BulkResult<CustomerAccountResponse> result = new BulkResult<>();
        for (int i = 0; i < requests.size(); i++) {
            try {
                result.addSuccess(create(requests.get(i)));
            } catch (RuntimeException ex) {
                log.warn("Bulk create of CustomerAccount failed at index {}: {}", i, ex.getMessage());
                result.addFailure(i, ex.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public BulkResult<CustomerAccountResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status) {
        BulkResult<CustomerAccountResponse> result = new BulkResult<>();
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
    public void exportCsv(CustomerAccountSearchCriteria criteria, Writer writer) {
        CsvWriter csv = new CsvWriter(writer);
        csv.writeRow(new java.util.ArrayList<Object>(CustomerAccountMapper.CSV_HEADER));
        int page = 0;
        Page<CustomerAccount> chunk;
        do {
            chunk = repository.findAll(CustomerAccountSpecifications.fromCriteria(tenant(), criteria),
                    org.springframework.data.domain.PageRequest.of(page++, EXPORT_PAGE_SIZE,
                            org.springframework.data.domain.Sort.by("code")));
            chunk.forEach(entity -> csv.writeRow(mapper.toCsvRow(entity)));
        } while (chunk.hasNext());
        csv.flush();
    }

    @Override
    public PageResponse<CustomerAccountResponse> findByCustomerId(UUID customerId, Pageable pageable) {
        return PageResponse.from(repository.findByTenantIdAndCustomerId(tenant(), customerId, pageable).map(mapper::toResponse));
    }
}
