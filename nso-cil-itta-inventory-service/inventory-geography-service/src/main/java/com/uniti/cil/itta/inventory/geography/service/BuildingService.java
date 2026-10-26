package com.uniti.cil.itta.inventory.geography.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Building records. */
public interface BuildingService {

    BuildingResponse get(UUID id);

    BuildingResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<BuildingResponse> search(BuildingSearchCriteria criteria, Pageable pageable);

    List<BuildingSummary> summaries(LifecycleStatus status);

    BuildingResponse create(BuildingCreateRequest request);

    BuildingResponse update(UUID id, BuildingUpdateRequest request);

    BuildingResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<BuildingResponse> bulkCreate(List<BuildingCreateRequest> requests);

    BulkResult<BuildingResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(BuildingSearchCriteria criteria, Writer writer);

    PageResponse<BuildingResponse> findBySiteId(UUID siteId, Pageable pageable);
}
