package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Utilization Sample records. */
public interface UtilizationSampleService {

    UtilizationSampleResponse get(UUID id);

    UtilizationSampleResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<UtilizationSampleResponse> search(UtilizationSampleSearchCriteria criteria, Pageable pageable);

    List<UtilizationSampleSummary> summaries(LifecycleStatus status);

    UtilizationSampleResponse create(UtilizationSampleCreateRequest request);

    UtilizationSampleResponse update(UUID id, UtilizationSampleUpdateRequest request);

    UtilizationSampleResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<UtilizationSampleResponse> bulkCreate(List<UtilizationSampleCreateRequest> requests);

    BulkResult<UtilizationSampleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(UtilizationSampleSearchCriteria criteria, Writer writer);

}
