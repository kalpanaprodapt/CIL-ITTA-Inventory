package com.uniti.cil.itta.inventory.topology.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Path Trace records. */
public interface PathTraceService {

    PathTraceResponse get(UUID id);

    PathTraceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PathTraceResponse> search(PathTraceSearchCriteria criteria, Pageable pageable);

    List<PathTraceSummary> summaries(LifecycleStatus status);

    PathTraceResponse create(PathTraceCreateRequest request);

    PathTraceResponse update(UUID id, PathTraceUpdateRequest request);

    PathTraceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PathTraceResponse> bulkCreate(List<PathTraceCreateRequest> requests);

    BulkResult<PathTraceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PathTraceSearchCriteria criteria, Writer writer);

}
