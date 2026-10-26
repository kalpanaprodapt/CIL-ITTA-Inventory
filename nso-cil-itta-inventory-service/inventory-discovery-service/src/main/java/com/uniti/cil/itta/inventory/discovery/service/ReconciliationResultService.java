package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Reconciliation Result records. */
public interface ReconciliationResultService {

    ReconciliationResultResponse get(UUID id);

    ReconciliationResultResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ReconciliationResultResponse> search(ReconciliationResultSearchCriteria criteria, Pageable pageable);

    List<ReconciliationResultSummary> summaries(LifecycleStatus status);

    ReconciliationResultResponse create(ReconciliationResultCreateRequest request);

    ReconciliationResultResponse update(UUID id, ReconciliationResultUpdateRequest request);

    ReconciliationResultResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ReconciliationResultResponse> bulkCreate(List<ReconciliationResultCreateRequest> requests);

    BulkResult<ReconciliationResultResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ReconciliationResultSearchCriteria criteria, Writer writer);

    PageResponse<ReconciliationResultResponse> findByJobId(UUID jobId, Pageable pageable);

    PageResponse<ReconciliationResultResponse> findByRuleId(UUID ruleId, Pageable pageable);
}
