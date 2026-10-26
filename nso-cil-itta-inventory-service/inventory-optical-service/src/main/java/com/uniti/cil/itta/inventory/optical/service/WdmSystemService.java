package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Wdm System records. */
public interface WdmSystemService {

    WdmSystemResponse get(UUID id);

    WdmSystemResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<WdmSystemResponse> search(WdmSystemSearchCriteria criteria, Pageable pageable);

    List<WdmSystemSummary> summaries(LifecycleStatus status);

    WdmSystemResponse create(WdmSystemCreateRequest request);

    WdmSystemResponse update(UUID id, WdmSystemUpdateRequest request);

    WdmSystemResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<WdmSystemResponse> bulkCreate(List<WdmSystemCreateRequest> requests);

    BulkResult<WdmSystemResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(WdmSystemSearchCriteria criteria, Writer writer);

    PageResponse<WdmSystemResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
