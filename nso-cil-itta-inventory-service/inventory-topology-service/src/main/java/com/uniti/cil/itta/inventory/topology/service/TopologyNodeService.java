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

/** Application service for Topology Node records. */
public interface TopologyNodeService {

    TopologyNodeResponse get(UUID id);

    TopologyNodeResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TopologyNodeResponse> search(TopologyNodeSearchCriteria criteria, Pageable pageable);

    List<TopologyNodeSummary> summaries(LifecycleStatus status);

    TopologyNodeResponse create(TopologyNodeCreateRequest request);

    TopologyNodeResponse update(UUID id, TopologyNodeUpdateRequest request);

    TopologyNodeResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TopologyNodeResponse> bulkCreate(List<TopologyNodeCreateRequest> requests);

    BulkResult<TopologyNodeResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TopologyNodeSearchCriteria criteria, Writer writer);

    PageResponse<TopologyNodeResponse> findByViewId(UUID viewId, Pageable pageable);
}
