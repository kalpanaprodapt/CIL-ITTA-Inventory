package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Hypervisor records. */
public interface HypervisorService {

    HypervisorResponse get(UUID id);

    HypervisorResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<HypervisorResponse> search(HypervisorSearchCriteria criteria, Pageable pageable);

    List<HypervisorSummary> summaries(LifecycleStatus status);

    HypervisorResponse create(HypervisorCreateRequest request);

    HypervisorResponse update(UUID id, HypervisorUpdateRequest request);

    HypervisorResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<HypervisorResponse> bulkCreate(List<HypervisorCreateRequest> requests);

    BulkResult<HypervisorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(HypervisorSearchCriteria criteria, Writer writer);

    PageResponse<HypervisorResponse> findByHostDeviceId(UUID hostDeviceId, Pageable pageable);
}
