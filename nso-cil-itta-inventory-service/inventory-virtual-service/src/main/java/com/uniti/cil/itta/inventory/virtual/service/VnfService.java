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

/** Application service for Vnf records. */
public interface VnfService {

    VnfResponse get(UUID id);

    VnfResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VnfResponse> search(VnfSearchCriteria criteria, Pageable pageable);

    List<VnfSummary> summaries(LifecycleStatus status);

    VnfResponse create(VnfCreateRequest request);

    VnfResponse update(UUID id, VnfUpdateRequest request);

    VnfResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VnfResponse> bulkCreate(List<VnfCreateRequest> requests);

    BulkResult<VnfResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VnfSearchCriteria criteria, Writer writer);

}
