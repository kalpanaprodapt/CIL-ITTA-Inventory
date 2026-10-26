package com.uniti.cil.itta.inventory.vendor.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Support Contract records. */
public interface SupportContractService {

    SupportContractResponse get(UUID id);

    SupportContractResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SupportContractResponse> search(SupportContractSearchCriteria criteria, Pageable pageable);

    List<SupportContractSummary> summaries(LifecycleStatus status);

    SupportContractResponse create(SupportContractCreateRequest request);

    SupportContractResponse update(UUID id, SupportContractUpdateRequest request);

    SupportContractResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SupportContractResponse> bulkCreate(List<SupportContractCreateRequest> requests);

    BulkResult<SupportContractResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SupportContractSearchCriteria criteria, Writer writer);

    PageResponse<SupportContractResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
