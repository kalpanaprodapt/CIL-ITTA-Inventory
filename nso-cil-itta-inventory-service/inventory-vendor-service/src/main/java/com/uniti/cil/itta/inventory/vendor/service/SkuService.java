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

/** Application service for Sku records. */
public interface SkuService {

    SkuResponse get(UUID id);

    SkuResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SkuResponse> search(SkuSearchCriteria criteria, Pageable pageable);

    List<SkuSummary> summaries(LifecycleStatus status);

    SkuResponse create(SkuCreateRequest request);

    SkuResponse update(UUID id, SkuUpdateRequest request);

    SkuResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SkuResponse> bulkCreate(List<SkuCreateRequest> requests);

    BulkResult<SkuResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SkuSearchCriteria criteria, Writer writer);

    PageResponse<SkuResponse> findByModelId(UUID modelId, Pageable pageable);
}
