package com.uniti.cil.itta.inventory.service.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Service Endpoint records. */
public interface ServiceEndpointService {

    ServiceEndpointResponse get(UUID id);

    ServiceEndpointResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ServiceEndpointResponse> search(ServiceEndpointSearchCriteria criteria, Pageable pageable);

    List<ServiceEndpointSummary> summaries(LifecycleStatus status);

    ServiceEndpointResponse create(ServiceEndpointCreateRequest request);

    ServiceEndpointResponse update(UUID id, ServiceEndpointUpdateRequest request);

    ServiceEndpointResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ServiceEndpointResponse> bulkCreate(List<ServiceEndpointCreateRequest> requests);

    BulkResult<ServiceEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ServiceEndpointSearchCriteria criteria, Writer writer);

    PageResponse<ServiceEndpointResponse> findByServiceId(UUID serviceId, Pageable pageable);

    PageResponse<ServiceEndpointResponse> findBySiteId(UUID siteId, Pageable pageable);

    PageResponse<ServiceEndpointResponse> findByPortId(UUID portId, Pageable pageable);
}
