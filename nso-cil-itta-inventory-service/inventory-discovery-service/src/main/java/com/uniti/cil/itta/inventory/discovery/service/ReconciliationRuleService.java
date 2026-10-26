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

/** Application service for Reconciliation Rule records. */
public interface ReconciliationRuleService {

    ReconciliationRuleResponse get(UUID id);

    ReconciliationRuleResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ReconciliationRuleResponse> search(ReconciliationRuleSearchCriteria criteria, Pageable pageable);

    List<ReconciliationRuleSummary> summaries(LifecycleStatus status);

    ReconciliationRuleResponse create(ReconciliationRuleCreateRequest request);

    ReconciliationRuleResponse update(UUID id, ReconciliationRuleUpdateRequest request);

    ReconciliationRuleResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ReconciliationRuleResponse> bulkCreate(List<ReconciliationRuleCreateRequest> requests);

    BulkResult<ReconciliationRuleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ReconciliationRuleSearchCriteria criteria, Writer writer);

}
