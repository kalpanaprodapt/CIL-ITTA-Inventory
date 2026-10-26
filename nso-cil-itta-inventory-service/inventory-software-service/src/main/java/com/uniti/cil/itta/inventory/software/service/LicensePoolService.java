package com.uniti.cil.itta.inventory.software.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for License Pool records. */
public interface LicensePoolService {

    LicensePoolResponse get(UUID id);

    LicensePoolResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<LicensePoolResponse> search(LicensePoolSearchCriteria criteria, Pageable pageable);

    List<LicensePoolSummary> summaries(LifecycleStatus status);

    LicensePoolResponse create(LicensePoolCreateRequest request);

    LicensePoolResponse update(UUID id, LicensePoolUpdateRequest request);

    LicensePoolResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<LicensePoolResponse> bulkCreate(List<LicensePoolCreateRequest> requests);

    BulkResult<LicensePoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(LicensePoolSearchCriteria criteria, Writer writer);

    PageResponse<LicensePoolResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
