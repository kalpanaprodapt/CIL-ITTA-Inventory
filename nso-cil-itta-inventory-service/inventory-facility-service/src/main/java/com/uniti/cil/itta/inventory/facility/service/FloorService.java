package com.uniti.cil.itta.inventory.facility.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Floor records. */
public interface FloorService {

    FloorResponse get(UUID id);

    FloorResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<FloorResponse> search(FloorSearchCriteria criteria, Pageable pageable);

    List<FloorSummary> summaries(LifecycleStatus status);

    FloorResponse create(FloorCreateRequest request);

    FloorResponse update(UUID id, FloorUpdateRequest request);

    FloorResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<FloorResponse> bulkCreate(List<FloorCreateRequest> requests);

    BulkResult<FloorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(FloorSearchCriteria criteria, Writer writer);

    PageResponse<FloorResponse> findByBuildingId(UUID buildingId, Pageable pageable);
}
