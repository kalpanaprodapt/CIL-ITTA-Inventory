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

/** Application service for License Key records. */
public interface LicenseKeyService {

    LicenseKeyResponse get(UUID id);

    LicenseKeyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<LicenseKeyResponse> search(LicenseKeySearchCriteria criteria, Pageable pageable);

    List<LicenseKeySummary> summaries(LifecycleStatus status);

    LicenseKeyResponse create(LicenseKeyCreateRequest request);

    LicenseKeyResponse update(UUID id, LicenseKeyUpdateRequest request);

    LicenseKeyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<LicenseKeyResponse> bulkCreate(List<LicenseKeyCreateRequest> requests);

    BulkResult<LicenseKeyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(LicenseKeySearchCriteria criteria, Writer writer);

    PageResponse<LicenseKeyResponse> findByPoolId(UUID poolId, Pageable pageable);

    PageResponse<LicenseKeyResponse> findByAssignedDeviceId(UUID assignedDeviceId, Pageable pageable);
}
