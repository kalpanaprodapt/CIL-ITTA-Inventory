package com.uniti.cil.itta.inventory.customer.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Customer Contact records. */
public interface CustomerContactService {

    CustomerContactResponse get(UUID id);

    CustomerContactResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CustomerContactResponse> search(CustomerContactSearchCriteria criteria, Pageable pageable);

    List<CustomerContactSummary> summaries(LifecycleStatus status);

    CustomerContactResponse create(CustomerContactCreateRequest request);

    CustomerContactResponse update(UUID id, CustomerContactUpdateRequest request);

    CustomerContactResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CustomerContactResponse> bulkCreate(List<CustomerContactCreateRequest> requests);

    BulkResult<CustomerContactResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CustomerContactSearchCriteria criteria, Writer writer);

    PageResponse<CustomerContactResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
