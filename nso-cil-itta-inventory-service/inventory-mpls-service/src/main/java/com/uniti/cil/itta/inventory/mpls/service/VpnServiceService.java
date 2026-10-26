package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Vpn Service records. */
public interface VpnServiceService {

    VpnServiceResponse get(UUID id);

    VpnServiceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VpnServiceResponse> search(VpnServiceSearchCriteria criteria, Pageable pageable);

    List<VpnServiceSummary> summaries(LifecycleStatus status);

    VpnServiceResponse create(VpnServiceCreateRequest request);

    VpnServiceResponse update(UUID id, VpnServiceUpdateRequest request);

    VpnServiceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VpnServiceResponse> bulkCreate(List<VpnServiceCreateRequest> requests);

    BulkResult<VpnServiceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VpnServiceSearchCriteria criteria, Writer writer);

    PageResponse<VpnServiceResponse> findByVrfId(UUID vrfId, Pageable pageable);

    PageResponse<VpnServiceResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
