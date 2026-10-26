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

/** Application service for Room records. */
public interface RoomService {

    RoomResponse get(UUID id);

    RoomResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RoomResponse> search(RoomSearchCriteria criteria, Pageable pageable);

    List<RoomSummary> summaries(LifecycleStatus status);

    RoomResponse create(RoomCreateRequest request);

    RoomResponse update(UUID id, RoomUpdateRequest request);

    RoomResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RoomResponse> bulkCreate(List<RoomCreateRequest> requests);

    BulkResult<RoomResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RoomSearchCriteria criteria, Writer writer);

    PageResponse<RoomResponse> findByFloorId(UUID floorId, Pageable pageable);
}
