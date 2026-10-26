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

/** Application service for Service Instance records. */
public interface ServiceInstanceService {

    ServiceInstanceResponse get(UUID id);

    ServiceInstanceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ServiceInstanceResponse> search(ServiceInstanceSearchCriteria criteria, Pageable pageable);

    List<ServiceInstanceSummary> summaries(LifecycleStatus status);

    ServiceInstanceResponse create(ServiceInstanceCreateRequest request);

    ServiceInstanceResponse update(UUID id, ServiceInstanceUpdateRequest request);

    ServiceInstanceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ServiceInstanceResponse> bulkCreate(List<ServiceInstanceCreateRequest> requests);

    BulkResult<ServiceInstanceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ServiceInstanceSearchCriteria criteria, Writer writer);

    PageResponse<ServiceInstanceResponse> findByCatalogItemId(UUID catalogItemId, Pageable pageable);

    PageResponse<ServiceInstanceResponse> findByCustomerId(UUID customerId, Pageable pageable);
}
