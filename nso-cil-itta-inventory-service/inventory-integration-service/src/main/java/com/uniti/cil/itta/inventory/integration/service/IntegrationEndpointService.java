package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Integration Endpoint records. */
public interface IntegrationEndpointService {

    IntegrationEndpointResponse get(UUID id);

    IntegrationEndpointResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<IntegrationEndpointResponse> search(IntegrationEndpointSearchCriteria criteria, Pageable pageable);

    List<IntegrationEndpointSummary> summaries(LifecycleStatus status);

    IntegrationEndpointResponse create(IntegrationEndpointCreateRequest request);

    IntegrationEndpointResponse update(UUID id, IntegrationEndpointUpdateRequest request);

    IntegrationEndpointResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<IntegrationEndpointResponse> bulkCreate(List<IntegrationEndpointCreateRequest> requests);

    BulkResult<IntegrationEndpointResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(IntegrationEndpointSearchCriteria criteria, Writer writer);

}
