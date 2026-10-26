package com.uniti.cil.itta.inventory.service.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Service Order records. */
public interface ServiceOrderService {

    ServiceOrderResponse get(UUID id);

    ServiceOrderResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ServiceOrderResponse> search(ServiceOrderSearchCriteria criteria, Pageable pageable);

    List<ServiceOrderSummary> summaries(LifecycleStatus status);

    ServiceOrderResponse create(ServiceOrderCreateRequest request);

    ServiceOrderResponse update(UUID id, ServiceOrderUpdateRequest request);

    ServiceOrderResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ServiceOrderResponse> bulkCreate(List<ServiceOrderCreateRequest> requests);

    BulkResult<ServiceOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ServiceOrderSearchCriteria criteria, Writer writer);

    PageResponse<ServiceOrderResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
