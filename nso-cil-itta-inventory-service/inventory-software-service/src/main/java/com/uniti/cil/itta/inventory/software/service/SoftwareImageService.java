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

/** Application service for Software Image records. */
public interface SoftwareImageService {

    SoftwareImageResponse get(UUID id);

    SoftwareImageResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SoftwareImageResponse> search(SoftwareImageSearchCriteria criteria, Pageable pageable);

    List<SoftwareImageSummary> summaries(LifecycleStatus status);

    SoftwareImageResponse create(SoftwareImageCreateRequest request);

    SoftwareImageResponse update(UUID id, SoftwareImageUpdateRequest request);

    SoftwareImageResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SoftwareImageResponse> bulkCreate(List<SoftwareImageCreateRequest> requests);

    BulkResult<SoftwareImageResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SoftwareImageSearchCriteria criteria, Writer writer);

    PageResponse<SoftwareImageResponse> findByVendorId(UUID vendorId, Pageable pageable);
}
