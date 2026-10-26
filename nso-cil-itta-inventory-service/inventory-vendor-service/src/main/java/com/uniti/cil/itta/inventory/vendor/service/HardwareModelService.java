package com.uniti.cil.itta.inventory.vendor.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Hardware Model records. */
public interface HardwareModelService {

    HardwareModelResponse get(UUID id);

    HardwareModelResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<HardwareModelResponse> search(HardwareModelSearchCriteria criteria, Pageable pageable);

    List<HardwareModelSummary> summaries(LifecycleStatus status);

    HardwareModelResponse create(HardwareModelCreateRequest request);

    HardwareModelResponse update(UUID id, HardwareModelUpdateRequest request);

    HardwareModelResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<HardwareModelResponse> bulkCreate(List<HardwareModelCreateRequest> requests);

    BulkResult<HardwareModelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(HardwareModelSearchCriteria criteria, Writer writer);

    PageResponse<HardwareModelResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
