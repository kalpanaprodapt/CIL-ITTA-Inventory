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

/** Application service for Depreciation Schedule records. */
public interface DepreciationScheduleService {

    DepreciationScheduleResponse get(UUID id);

    DepreciationScheduleResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<DepreciationScheduleResponse> search(DepreciationScheduleSearchCriteria criteria, Pageable pageable);

    List<DepreciationScheduleSummary> summaries(LifecycleStatus status);

    DepreciationScheduleResponse create(DepreciationScheduleCreateRequest request);

    DepreciationScheduleResponse update(UUID id, DepreciationScheduleUpdateRequest request);

    DepreciationScheduleResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<DepreciationScheduleResponse> bulkCreate(List<DepreciationScheduleCreateRequest> requests);

    BulkResult<DepreciationScheduleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(DepreciationScheduleSearchCriteria criteria, Writer writer);

    PageResponse<DepreciationScheduleResponse> findByAssetId(UUID assetId, Pageable pageable);
}
