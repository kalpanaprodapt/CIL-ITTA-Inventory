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

/** Application service for Service Dependency records. */
public interface ServiceDependencyService {

    ServiceDependencyResponse get(UUID id);

    ServiceDependencyResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ServiceDependencyResponse> search(ServiceDependencySearchCriteria criteria, Pageable pageable);

    List<ServiceDependencySummary> summaries(LifecycleStatus status);

    ServiceDependencyResponse create(ServiceDependencyCreateRequest request);

    ServiceDependencyResponse update(UUID id, ServiceDependencyUpdateRequest request);

    ServiceDependencyResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ServiceDependencyResponse> bulkCreate(List<ServiceDependencyCreateRequest> requests);

    BulkResult<ServiceDependencyResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ServiceDependencySearchCriteria criteria, Writer writer);

    PageResponse<ServiceDependencyResponse> findByServiceId(UUID serviceId, Pageable pageable);

    PageResponse<ServiceDependencyResponse> findByDependsOnId(UUID dependsOnId, Pageable pageable);
}
