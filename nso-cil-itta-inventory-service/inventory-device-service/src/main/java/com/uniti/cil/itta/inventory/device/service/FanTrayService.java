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

/** Application service for Fan Tray records. */
public interface FanTrayService {

    FanTrayResponse get(UUID id);

    FanTrayResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<FanTrayResponse> search(FanTraySearchCriteria criteria, Pageable pageable);

    List<FanTraySummary> summaries(LifecycleStatus status);

    FanTrayResponse create(FanTrayCreateRequest request);

    FanTrayResponse update(UUID id, FanTrayUpdateRequest request);

    FanTrayResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<FanTrayResponse> bulkCreate(List<FanTrayCreateRequest> requests);

    BulkResult<FanTrayResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(FanTraySearchCriteria criteria, Writer writer);

    PageResponse<FanTrayResponse> findByChassisId(UUID chassisId, Pageable pageable);
}
