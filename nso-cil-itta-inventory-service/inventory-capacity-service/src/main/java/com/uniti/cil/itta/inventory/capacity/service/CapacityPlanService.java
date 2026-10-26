package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Capacity Plan records. */
public interface CapacityPlanService {

    CapacityPlanResponse get(UUID id);

    CapacityPlanResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CapacityPlanResponse> search(CapacityPlanSearchCriteria criteria, Pageable pageable);

    List<CapacityPlanSummary> summaries(LifecycleStatus status);

    CapacityPlanResponse create(CapacityPlanCreateRequest request);

    CapacityPlanResponse update(UUID id, CapacityPlanUpdateRequest request);

    CapacityPlanResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CapacityPlanResponse> bulkCreate(List<CapacityPlanCreateRequest> requests);

    BulkResult<CapacityPlanResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CapacityPlanSearchCriteria criteria, Writer writer);

}
