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

/** Application service for Sync Job records. */
public interface SyncJobService {

    SyncJobResponse get(UUID id);

    SyncJobResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SyncJobResponse> search(SyncJobSearchCriteria criteria, Pageable pageable);

    List<SyncJobSummary> summaries(LifecycleStatus status);

    SyncJobResponse create(SyncJobCreateRequest request);

    SyncJobResponse update(UUID id, SyncJobUpdateRequest request);

    SyncJobResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SyncJobResponse> bulkCreate(List<SyncJobCreateRequest> requests);

    BulkResult<SyncJobResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SyncJobSearchCriteria criteria, Writer writer);

    PageResponse<SyncJobResponse> findByEndpointId(UUID endpointId, Pageable pageable);
}
