package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Field Task records. */
public interface FieldTaskService {

    FieldTaskResponse get(UUID id);

    FieldTaskResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<FieldTaskResponse> search(FieldTaskSearchCriteria criteria, Pageable pageable);

    List<FieldTaskSummary> summaries(LifecycleStatus status);

    FieldTaskResponse create(FieldTaskCreateRequest request);

    FieldTaskResponse update(UUID id, FieldTaskUpdateRequest request);

    FieldTaskResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<FieldTaskResponse> bulkCreate(List<FieldTaskCreateRequest> requests);

    BulkResult<FieldTaskResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(FieldTaskSearchCriteria criteria, Writer writer);

    PageResponse<FieldTaskResponse> findByWorkOrderId(UUID workOrderId, Pageable pageable);
}
