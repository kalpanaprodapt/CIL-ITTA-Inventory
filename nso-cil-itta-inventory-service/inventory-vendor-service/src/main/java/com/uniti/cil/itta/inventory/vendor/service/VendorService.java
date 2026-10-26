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

/** Application service for Vendor records. */
public interface VendorService {

    VendorResponse get(UUID id);

    VendorResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VendorResponse> search(VendorSearchCriteria criteria, Pageable pageable);

    List<VendorSummary> summaries(LifecycleStatus status);

    VendorResponse create(VendorCreateRequest request);

    VendorResponse update(UUID id, VendorUpdateRequest request);

    VendorResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VendorResponse> bulkCreate(List<VendorCreateRequest> requests);

    BulkResult<VendorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VendorSearchCriteria criteria, Writer writer);

}
