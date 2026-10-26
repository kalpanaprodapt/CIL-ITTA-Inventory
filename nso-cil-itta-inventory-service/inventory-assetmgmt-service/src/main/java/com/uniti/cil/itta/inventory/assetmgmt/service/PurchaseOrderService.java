package com.uniti.cil.itta.inventory.assetmgmt.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Purchase Order records. */
public interface PurchaseOrderService {

    PurchaseOrderResponse get(UUID id);

    PurchaseOrderResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PurchaseOrderResponse> search(PurchaseOrderSearchCriteria criteria, Pageable pageable);

    List<PurchaseOrderSummary> summaries(LifecycleStatus status);

    PurchaseOrderResponse create(PurchaseOrderCreateRequest request);

    PurchaseOrderResponse update(UUID id, PurchaseOrderUpdateRequest request);

    PurchaseOrderResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PurchaseOrderResponse> bulkCreate(List<PurchaseOrderCreateRequest> requests);

    BulkResult<PurchaseOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PurchaseOrderSearchCriteria criteria, Writer writer);

    PageResponse<PurchaseOrderResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
