package com.uniti.cil.itta.inventory.software.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Patch records. */
public interface PatchService {

    PatchResponse get(UUID id);

    PatchResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PatchResponse> search(PatchSearchCriteria criteria, Pageable pageable);

    List<PatchSummary> summaries(LifecycleStatus status);

    PatchResponse create(PatchCreateRequest request);

    PatchResponse update(UUID id, PatchUpdateRequest request);

    PatchResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PatchResponse> bulkCreate(List<PatchCreateRequest> requests);

    BulkResult<PatchResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PatchSearchCriteria criteria, Writer writer);

    PageResponse<PatchResponse> findByVersionId(UUID versionId, Pageable pageable);
}
