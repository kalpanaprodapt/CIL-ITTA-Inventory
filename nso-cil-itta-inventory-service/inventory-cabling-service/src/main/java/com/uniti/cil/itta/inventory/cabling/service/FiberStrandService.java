package com.uniti.cil.itta.inventory.cabling.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Fiber Strand records. */
public interface FiberStrandService {

    FiberStrandResponse get(UUID id);

    FiberStrandResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<FiberStrandResponse> search(FiberStrandSearchCriteria criteria, Pageable pageable);

    List<FiberStrandSummary> summaries(LifecycleStatus status);

    FiberStrandResponse create(FiberStrandCreateRequest request);

    FiberStrandResponse update(UUID id, FiberStrandUpdateRequest request);

    FiberStrandResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<FiberStrandResponse> bulkCreate(List<FiberStrandCreateRequest> requests);

    BulkResult<FiberStrandResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(FiberStrandSearchCriteria criteria, Writer writer);

    PageResponse<FiberStrandResponse> findByCableId(UUID cableId, Pageable pageable);
}
