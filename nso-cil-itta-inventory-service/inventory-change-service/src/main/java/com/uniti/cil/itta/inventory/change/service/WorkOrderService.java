package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Work Order records. */
public interface WorkOrderService {

    WorkOrderResponse get(UUID id);

    WorkOrderResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<WorkOrderResponse> search(WorkOrderSearchCriteria criteria, Pageable pageable);

    List<WorkOrderSummary> summaries(LifecycleStatus status);

    WorkOrderResponse create(WorkOrderCreateRequest request);

    WorkOrderResponse update(UUID id, WorkOrderUpdateRequest request);

    WorkOrderResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<WorkOrderResponse> bulkCreate(List<WorkOrderCreateRequest> requests);

    BulkResult<WorkOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(WorkOrderSearchCriteria criteria, Writer writer);

    PageResponse<WorkOrderResponse> findByAssignedTechnicianId(UUID assignedTechnicianId, Pageable pageable);
}
