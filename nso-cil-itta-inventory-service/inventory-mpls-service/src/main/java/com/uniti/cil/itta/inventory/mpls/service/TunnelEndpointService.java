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

/** Application service for Tunnel Endpoint records. */
public interface TunnelEndpointService {

    TunnelEndpointResponse get(UUID id);

    TunnelEndpointResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TunnelEndpointResponse> search(TunnelEndpointSearchCriteria criteria, Pageable pageable);

    List<TunnelEndpointSummary> summaries(LifecycleStatus status);

    TunnelEndpointResponse create(TunnelEndpointCreateRequest request);

    TunnelEndpointResponse update(UUID id, TunnelEndpointUpdateRequest request);

    TunnelEndpointResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TunnelEndpointResponse> bulkCreate(List<TunnelEndpointCreateRequest> requests);

    BulkResult<TunnelEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TunnelEndpointSearchCriteria criteria, Writer writer);

    PageResponse<TunnelEndpointResponse> findByDeviceId(UUID deviceId, Pageable pageable);
}
