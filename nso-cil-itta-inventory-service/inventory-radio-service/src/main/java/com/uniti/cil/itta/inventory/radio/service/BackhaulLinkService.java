package com.uniti.cil.itta.inventory.radio.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Backhaul Link records. */
public interface BackhaulLinkService {

    BackhaulLinkResponse get(UUID id);

    BackhaulLinkResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<BackhaulLinkResponse> search(BackhaulLinkSearchCriteria criteria, Pageable pageable);

    List<BackhaulLinkSummary> summaries(LifecycleStatus status);

    BackhaulLinkResponse create(BackhaulLinkCreateRequest request);

    BackhaulLinkResponse update(UUID id, BackhaulLinkUpdateRequest request);

    BackhaulLinkResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<BackhaulLinkResponse> bulkCreate(List<BackhaulLinkCreateRequest> requests);

    BulkResult<BackhaulLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(BackhaulLinkSearchCriteria criteria, Writer writer);

    PageResponse<BackhaulLinkResponse> findByBaseStationId(UUID baseStationId, Pageable pageable);
}
