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

/** Application service for Asset records. */
public interface AssetService {

    AssetResponse get(UUID id);

    AssetResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AssetResponse> search(AssetSearchCriteria criteria, Pageable pageable);

    List<AssetSummary> summaries(LifecycleStatus status);

    AssetResponse create(AssetCreateRequest request);

    AssetResponse update(UUID id, AssetUpdateRequest request);

    AssetResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AssetResponse> bulkCreate(List<AssetCreateRequest> requests);

    BulkResult<AssetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AssetSearchCriteria criteria, Writer writer);

}
