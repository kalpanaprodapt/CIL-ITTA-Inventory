package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Maintenance Window records. */
public interface MaintenanceWindowService {

    MaintenanceWindowResponse get(UUID id);

    MaintenanceWindowResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<MaintenanceWindowResponse> search(MaintenanceWindowSearchCriteria criteria, Pageable pageable);

    List<MaintenanceWindowSummary> summaries(LifecycleStatus status);

    MaintenanceWindowResponse create(MaintenanceWindowCreateRequest request);

    MaintenanceWindowResponse update(UUID id, MaintenanceWindowUpdateRequest request);

    MaintenanceWindowResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<MaintenanceWindowResponse> bulkCreate(List<MaintenanceWindowCreateRequest> requests);

    BulkResult<MaintenanceWindowResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(MaintenanceWindowSearchCriteria criteria, Writer writer);

}
