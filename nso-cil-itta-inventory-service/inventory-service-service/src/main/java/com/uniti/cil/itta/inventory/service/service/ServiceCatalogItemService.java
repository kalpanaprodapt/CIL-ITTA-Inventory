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

/** Application service for Service Catalog Item records. */
public interface ServiceCatalogItemService {

    ServiceCatalogItemResponse get(UUID id);

    ServiceCatalogItemResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ServiceCatalogItemResponse> search(ServiceCatalogItemSearchCriteria criteria, Pageable pageable);

    List<ServiceCatalogItemSummary> summaries(LifecycleStatus status);

    ServiceCatalogItemResponse create(ServiceCatalogItemCreateRequest request);

    ServiceCatalogItemResponse update(UUID id, ServiceCatalogItemUpdateRequest request);

    ServiceCatalogItemResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ServiceCatalogItemResponse> bulkCreate(List<ServiceCatalogItemCreateRequest> requests);

    BulkResult<ServiceCatalogItemResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ServiceCatalogItemSearchCriteria criteria, Writer writer);

}
