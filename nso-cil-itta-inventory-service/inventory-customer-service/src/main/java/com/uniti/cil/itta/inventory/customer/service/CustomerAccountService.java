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

/** Application service for Customer Account records. */
public interface CustomerAccountService {

    CustomerAccountResponse get(UUID id);

    CustomerAccountResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CustomerAccountResponse> search(CustomerAccountSearchCriteria criteria, Pageable pageable);

    List<CustomerAccountSummary> summaries(LifecycleStatus status);

    CustomerAccountResponse create(CustomerAccountCreateRequest request);

    CustomerAccountResponse update(UUID id, CustomerAccountUpdateRequest request);

    CustomerAccountResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CustomerAccountResponse> bulkCreate(List<CustomerAccountCreateRequest> requests);

    BulkResult<CustomerAccountResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CustomerAccountSearchCriteria criteria, Writer writer);

    PageResponse<CustomerAccountResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
