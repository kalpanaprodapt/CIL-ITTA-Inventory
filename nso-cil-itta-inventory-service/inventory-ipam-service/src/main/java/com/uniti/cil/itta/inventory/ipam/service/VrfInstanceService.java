package com.uniti.cil.itta.inventory.ipam.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Vrf Instance records. */
public interface VrfInstanceService {

    VrfInstanceResponse get(UUID id);

    VrfInstanceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VrfInstanceResponse> search(VrfInstanceSearchCriteria criteria, Pageable pageable);

    List<VrfInstanceSummary> summaries(LifecycleStatus status);

    VrfInstanceResponse create(VrfInstanceCreateRequest request);

    VrfInstanceResponse update(UUID id, VrfInstanceUpdateRequest request);

    VrfInstanceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VrfInstanceResponse> bulkCreate(List<VrfInstanceCreateRequest> requests);

    BulkResult<VrfInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VrfInstanceSearchCriteria criteria, Writer writer);

    PageResponse<VrfInstanceResponse> findByDeviceId(UUID deviceId, Pageable pageable);
}
