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

/** Application service for Customer records. */
public interface CustomerService {

    CustomerResponse get(UUID id);

    CustomerResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CustomerResponse> search(CustomerSearchCriteria criteria, Pageable pageable);

    List<CustomerSummary> summaries(LifecycleStatus status);

    CustomerResponse create(CustomerCreateRequest request);

    CustomerResponse update(UUID id, CustomerUpdateRequest request);

    CustomerResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CustomerResponse> bulkCreate(List<CustomerCreateRequest> requests);

    BulkResult<CustomerResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CustomerSearchCriteria criteria, Writer writer);

}
