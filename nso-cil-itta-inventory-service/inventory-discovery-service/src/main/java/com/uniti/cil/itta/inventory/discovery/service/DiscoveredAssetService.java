package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Discovered Asset records. */
public interface DiscoveredAssetService {

    DiscoveredAssetResponse get(UUID id);

    DiscoveredAssetResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<DiscoveredAssetResponse> search(DiscoveredAssetSearchCriteria criteria, Pageable pageable);

    List<DiscoveredAssetSummary> summaries(LifecycleStatus status);

    DiscoveredAssetResponse create(DiscoveredAssetCreateRequest request);

    DiscoveredAssetResponse update(UUID id, DiscoveredAssetUpdateRequest request);

    DiscoveredAssetResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<DiscoveredAssetResponse> bulkCreate(List<DiscoveredAssetCreateRequest> requests);

    BulkResult<DiscoveredAssetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(DiscoveredAssetSearchCriteria criteria, Writer writer);

    PageResponse<DiscoveredAssetResponse> findByJobId(UUID jobId, Pageable pageable);

    PageResponse<DiscoveredAssetResponse> findByMatchedAssetId(UUID matchedAssetId, Pageable pageable);
}
