package com.uniti.cil.itta.inventory.cabling.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Patch Panel records. */
public interface PatchPanelService {

    PatchPanelResponse get(UUID id);

    PatchPanelResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PatchPanelResponse> search(PatchPanelSearchCriteria criteria, Pageable pageable);

    List<PatchPanelSummary> summaries(LifecycleStatus status);

    PatchPanelResponse create(PatchPanelCreateRequest request);

    PatchPanelResponse update(UUID id, PatchPanelUpdateRequest request);

    PatchPanelResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PatchPanelResponse> bulkCreate(List<PatchPanelCreateRequest> requests);

    BulkResult<PatchPanelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PatchPanelSearchCriteria criteria, Writer writer);

    PageResponse<PatchPanelResponse> findByRoomId(UUID roomId, Pageable pageable);

    PageResponse<PatchPanelResponse> findByRackId(UUID rackId, Pageable pageable);
}
