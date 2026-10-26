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

/** Application service for Contract records. */
public interface ContractService {

    ContractResponse get(UUID id);

    ContractResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ContractResponse> search(ContractSearchCriteria criteria, Pageable pageable);

    List<ContractSummary> summaries(LifecycleStatus status);

    ContractResponse create(ContractCreateRequest request);

    ContractResponse update(UUID id, ContractUpdateRequest request);

    ContractResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ContractResponse> bulkCreate(List<ContractCreateRequest> requests);

    BulkResult<ContractResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ContractSearchCriteria criteria, Writer writer);

    PageResponse<ContractResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
