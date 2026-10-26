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

/** Application service for Power Supply records. */
public interface PowerSupplyService {

    PowerSupplyResponse get(UUID id);

    PowerSupplyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PowerSupplyResponse> search(PowerSupplySearchCriteria criteria, Pageable pageable);

    List<PowerSupplySummary> summaries(LifecycleStatus status);

    PowerSupplyResponse create(PowerSupplyCreateRequest request);

    PowerSupplyResponse update(UUID id, PowerSupplyUpdateRequest request);

    PowerSupplyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PowerSupplyResponse> bulkCreate(List<PowerSupplyCreateRequest> requests);

    BulkResult<PowerSupplyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PowerSupplySearchCriteria criteria, Writer writer);

    PageResponse<PowerSupplyResponse> findByChassisId(UUID chassisId, Pageable pageable);

    PageResponse<PowerSupplyResponse> findByFeedId(UUID feedId, Pageable pageable);
}
