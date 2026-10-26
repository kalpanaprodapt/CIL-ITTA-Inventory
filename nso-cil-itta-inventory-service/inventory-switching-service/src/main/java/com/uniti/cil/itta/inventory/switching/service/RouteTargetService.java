package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Route Target records. */
public interface RouteTargetService {

    RouteTargetResponse get(UUID id);

    RouteTargetResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RouteTargetResponse> search(RouteTargetSearchCriteria criteria, Pageable pageable);

    List<RouteTargetSummary> summaries(LifecycleStatus status);

    RouteTargetResponse create(RouteTargetCreateRequest request);

    RouteTargetResponse update(UUID id, RouteTargetUpdateRequest request);

    RouteTargetResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RouteTargetResponse> bulkCreate(List<RouteTargetCreateRequest> requests);

    BulkResult<RouteTargetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RouteTargetSearchCriteria criteria, Writer writer);

    PageResponse<RouteTargetResponse> findByVrfId(UUID vrfId, Pageable pageable);
}
