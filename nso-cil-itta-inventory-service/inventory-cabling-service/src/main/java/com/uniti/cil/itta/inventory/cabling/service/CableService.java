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

/** Application service for Cable records. */
public interface CableService {

    CableResponse get(UUID id);

    CableResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CableResponse> search(CableSearchCriteria criteria, Pageable pageable);

    List<CableSummary> summaries(LifecycleStatus status);

    CableResponse create(CableCreateRequest request);

    CableResponse update(UUID id, CableUpdateRequest request);

    CableResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CableResponse> bulkCreate(List<CableCreateRequest> requests);

    BulkResult<CableResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CableSearchCriteria criteria, Writer writer);

    PageResponse<CableResponse> findByAEndPortId(UUID aEndPortId, Pageable pageable);

    PageResponse<CableResponse> findByZEndPortId(UUID zEndPortId, Pageable pageable);
}
