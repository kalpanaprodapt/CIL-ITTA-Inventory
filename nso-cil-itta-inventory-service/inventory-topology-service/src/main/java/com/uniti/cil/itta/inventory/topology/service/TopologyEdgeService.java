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

/** Application service for Topology Edge records. */
public interface TopologyEdgeService {

    TopologyEdgeResponse get(UUID id);

    TopologyEdgeResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TopologyEdgeResponse> search(TopologyEdgeSearchCriteria criteria, Pageable pageable);

    List<TopologyEdgeSummary> summaries(LifecycleStatus status);

    TopologyEdgeResponse create(TopologyEdgeCreateRequest request);

    TopologyEdgeResponse update(UUID id, TopologyEdgeUpdateRequest request);

    TopologyEdgeResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TopologyEdgeResponse> bulkCreate(List<TopologyEdgeCreateRequest> requests);

    BulkResult<TopologyEdgeResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TopologyEdgeSearchCriteria criteria, Writer writer);

    PageResponse<TopologyEdgeResponse> findByViewId(UUID viewId, Pageable pageable);

    PageResponse<TopologyEdgeResponse> findBySourceNodeId(UUID sourceNodeId, Pageable pageable);

    PageResponse<TopologyEdgeResponse> findByTargetNodeId(UUID targetNodeId, Pageable pageable);
}
