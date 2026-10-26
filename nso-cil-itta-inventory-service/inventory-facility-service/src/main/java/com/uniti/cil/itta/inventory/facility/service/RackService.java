package com.uniti.cil.itta.inventory.facility.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Rack records. */
public interface RackService {

    RackResponse get(UUID id);

    RackResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RackResponse> search(RackSearchCriteria criteria, Pageable pageable);

    List<RackSummary> summaries(LifecycleStatus status);

    RackResponse create(RackCreateRequest request);

    RackResponse update(UUID id, RackUpdateRequest request);

    RackResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RackResponse> bulkCreate(List<RackCreateRequest> requests);

    BulkResult<RackResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RackSearchCriteria criteria, Writer writer);

    PageResponse<RackResponse> findByRoomId(UUID roomId, Pageable pageable);
}
