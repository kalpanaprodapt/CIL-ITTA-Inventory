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

/** Application service for Rack Unit records. */
public interface RackUnitService {

    RackUnitResponse get(UUID id);

    RackUnitResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RackUnitResponse> search(RackUnitSearchCriteria criteria, Pageable pageable);

    List<RackUnitSummary> summaries(LifecycleStatus status);

    RackUnitResponse create(RackUnitCreateRequest request);

    RackUnitResponse update(UUID id, RackUnitUpdateRequest request);

    RackUnitResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RackUnitResponse> bulkCreate(List<RackUnitCreateRequest> requests);

    BulkResult<RackUnitResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RackUnitSearchCriteria criteria, Writer writer);

    PageResponse<RackUnitResponse> findByRackId(UUID rackId, Pageable pageable);

    PageResponse<RackUnitResponse> findByMountedDeviceId(UUID mountedDeviceId, Pageable pageable);
}
