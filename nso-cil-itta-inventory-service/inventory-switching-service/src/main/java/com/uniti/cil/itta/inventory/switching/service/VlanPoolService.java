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

/** Application service for Vlan Pool records. */
public interface VlanPoolService {

    VlanPoolResponse get(UUID id);

    VlanPoolResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VlanPoolResponse> search(VlanPoolSearchCriteria criteria, Pageable pageable);

    List<VlanPoolSummary> summaries(LifecycleStatus status);

    VlanPoolResponse create(VlanPoolCreateRequest request);

    VlanPoolResponse update(UUID id, VlanPoolUpdateRequest request);

    VlanPoolResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VlanPoolResponse> bulkCreate(List<VlanPoolCreateRequest> requests);

    BulkResult<VlanPoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VlanPoolSearchCriteria criteria, Writer writer);

}
