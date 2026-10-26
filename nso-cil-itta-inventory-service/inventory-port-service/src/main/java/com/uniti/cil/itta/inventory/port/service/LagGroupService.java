package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Lag Group records. */
public interface LagGroupService {

    LagGroupResponse get(UUID id);

    LagGroupResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<LagGroupResponse> search(LagGroupSearchCriteria criteria, Pageable pageable);

    List<LagGroupSummary> summaries(LifecycleStatus status);

    LagGroupResponse create(LagGroupCreateRequest request);

    LagGroupResponse update(UUID id, LagGroupUpdateRequest request);

    LagGroupResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<LagGroupResponse> bulkCreate(List<LagGroupCreateRequest> requests);

    BulkResult<LagGroupResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(LagGroupSearchCriteria criteria, Writer writer);

    PageResponse<LagGroupResponse> findByDeviceId(UUID deviceId, Pageable pageable);
}
