package com.uniti.cil.itta.inventory.device.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Chassis records. */
public interface ChassisService {

    ChassisResponse get(UUID id);

    ChassisResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ChassisResponse> search(ChassisSearchCriteria criteria, Pageable pageable);

    List<ChassisSummary> summaries(LifecycleStatus status);

    ChassisResponse create(ChassisCreateRequest request);

    ChassisResponse update(UUID id, ChassisUpdateRequest request);

    ChassisResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ChassisResponse> bulkCreate(List<ChassisCreateRequest> requests);

    BulkResult<ChassisResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ChassisSearchCriteria criteria, Writer writer);

    PageResponse<ChassisResponse> findByDeviceId(UUID deviceId, Pageable pageable);
}
