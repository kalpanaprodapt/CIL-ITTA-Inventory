package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Sync Mapping records. */
public interface SyncMappingService {

    SyncMappingResponse get(UUID id);

    SyncMappingResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SyncMappingResponse> search(SyncMappingSearchCriteria criteria, Pageable pageable);

    List<SyncMappingSummary> summaries(LifecycleStatus status);

    SyncMappingResponse create(SyncMappingCreateRequest request);

    SyncMappingResponse update(UUID id, SyncMappingUpdateRequest request);

    SyncMappingResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SyncMappingResponse> bulkCreate(List<SyncMappingCreateRequest> requests);

    BulkResult<SyncMappingResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SyncMappingSearchCriteria criteria, Writer writer);

    PageResponse<SyncMappingResponse> findByJobId(UUID jobId, Pageable pageable);
}
