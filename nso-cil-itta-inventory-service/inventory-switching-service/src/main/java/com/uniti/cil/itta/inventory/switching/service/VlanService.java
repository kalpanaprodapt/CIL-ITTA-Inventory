package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Vlan records. */
public interface VlanService {

    VlanResponse get(UUID id);

    VlanResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VlanResponse> search(VlanSearchCriteria criteria, Pageable pageable);

    List<VlanSummary> summaries(LifecycleStatus status);

    VlanResponse create(VlanCreateRequest request);

    VlanResponse update(UUID id, VlanUpdateRequest request);

    VlanResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VlanResponse> bulkCreate(List<VlanCreateRequest> requests);

    BulkResult<VlanResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VlanSearchCriteria criteria, Writer writer);

    PageResponse<VlanResponse> findByPoolId(UUID poolId, Pageable pageable);
}
