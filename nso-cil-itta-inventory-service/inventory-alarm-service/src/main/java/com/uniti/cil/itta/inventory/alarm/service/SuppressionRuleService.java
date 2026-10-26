package com.uniti.cil.itta.inventory.alarm.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Suppression Rule records. */
public interface SuppressionRuleService {

    SuppressionRuleResponse get(UUID id);

    SuppressionRuleResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SuppressionRuleResponse> search(SuppressionRuleSearchCriteria criteria, Pageable pageable);

    List<SuppressionRuleSummary> summaries(LifecycleStatus status);

    SuppressionRuleResponse create(SuppressionRuleCreateRequest request);

    SuppressionRuleResponse update(UUID id, SuppressionRuleUpdateRequest request);

    SuppressionRuleResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SuppressionRuleResponse> bulkCreate(List<SuppressionRuleCreateRequest> requests);

    BulkResult<SuppressionRuleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SuppressionRuleSearchCriteria criteria, Writer writer);

}
