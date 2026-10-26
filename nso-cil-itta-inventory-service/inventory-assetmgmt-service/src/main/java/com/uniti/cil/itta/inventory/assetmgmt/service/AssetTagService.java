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

/** Application service for Asset Tag records. */
public interface AssetTagService {

    AssetTagResponse get(UUID id);

    AssetTagResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AssetTagResponse> search(AssetTagSearchCriteria criteria, Pageable pageable);

    List<AssetTagSummary> summaries(LifecycleStatus status);

    AssetTagResponse create(AssetTagCreateRequest request);

    AssetTagResponse update(UUID id, AssetTagUpdateRequest request);

    AssetTagResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AssetTagResponse> bulkCreate(List<AssetTagCreateRequest> requests);

    BulkResult<AssetTagResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AssetTagSearchCriteria criteria, Writer writer);

    PageResponse<AssetTagResponse> findByAssetId(UUID assetId, Pageable pageable);
}
