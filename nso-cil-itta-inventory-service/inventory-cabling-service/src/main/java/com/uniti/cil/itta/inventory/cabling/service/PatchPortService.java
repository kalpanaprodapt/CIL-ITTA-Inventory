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

/** Application service for Patch Port records. */
public interface PatchPortService {

    PatchPortResponse get(UUID id);

    PatchPortResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PatchPortResponse> search(PatchPortSearchCriteria criteria, Pageable pageable);

    List<PatchPortSummary> summaries(LifecycleStatus status);

    PatchPortResponse create(PatchPortCreateRequest request);

    PatchPortResponse update(UUID id, PatchPortUpdateRequest request);

    PatchPortResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PatchPortResponse> bulkCreate(List<PatchPortCreateRequest> requests);

    BulkResult<PatchPortResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PatchPortSearchCriteria criteria, Writer writer);

    PageResponse<PatchPortResponse> findByPanelId(UUID panelId, Pageable pageable);

    PageResponse<PatchPortResponse> findByConnectedCableId(UUID connectedCableId, Pageable pageable);
}
