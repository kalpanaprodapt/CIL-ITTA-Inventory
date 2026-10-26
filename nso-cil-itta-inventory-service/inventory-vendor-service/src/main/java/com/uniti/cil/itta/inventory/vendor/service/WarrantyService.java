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

/** Application service for Warranty records. */
public interface WarrantyService {

    WarrantyResponse get(UUID id);

    WarrantyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<WarrantyResponse> search(WarrantySearchCriteria criteria, Pageable pageable);

    List<WarrantySummary> summaries(LifecycleStatus status);

    WarrantyResponse create(WarrantyCreateRequest request);

    WarrantyResponse update(UUID id, WarrantyUpdateRequest request);

    WarrantyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<WarrantyResponse> bulkCreate(List<WarrantyCreateRequest> requests);

    BulkResult<WarrantyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(WarrantySearchCriteria criteria, Writer writer);

    PageResponse<WarrantyResponse> findByAssetId(UUID assetId, Pageable pageable);
}
