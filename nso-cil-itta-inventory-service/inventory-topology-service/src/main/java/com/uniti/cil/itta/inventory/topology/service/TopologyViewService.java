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

/** Application service for Topology View records. */
public interface TopologyViewService {

    TopologyViewResponse get(UUID id);

    TopologyViewResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TopologyViewResponse> search(TopologyViewSearchCriteria criteria, Pageable pageable);

    List<TopologyViewSummary> summaries(LifecycleStatus status);

    TopologyViewResponse create(TopologyViewCreateRequest request);

    TopologyViewResponse update(UUID id, TopologyViewUpdateRequest request);

    TopologyViewResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TopologyViewResponse> bulkCreate(List<TopologyViewCreateRequest> requests);

    BulkResult<TopologyViewResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TopologyViewSearchCriteria criteria, Writer writer);

}
