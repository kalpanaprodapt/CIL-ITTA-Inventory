package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Otn Link records. */
public interface OtnLinkService {

    OtnLinkResponse get(UUID id);

    OtnLinkResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<OtnLinkResponse> search(OtnLinkSearchCriteria criteria, Pageable pageable);

    List<OtnLinkSummary> summaries(LifecycleStatus status);

    OtnLinkResponse create(OtnLinkCreateRequest request);

    OtnLinkResponse update(UUID id, OtnLinkUpdateRequest request);

    OtnLinkResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<OtnLinkResponse> bulkCreate(List<OtnLinkCreateRequest> requests);

    BulkResult<OtnLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(OtnLinkSearchCriteria criteria, Writer writer);

    PageResponse<OtnLinkResponse> findByANodeId(UUID aNodeId, Pageable pageable);

    PageResponse<OtnLinkResponse> findByZNodeId(UUID zNodeId, Pageable pageable);
}
