package com.uniti.cil.itta.inventory.compliance.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Compliance Rule records. */
public interface ComplianceRuleService {

    ComplianceRuleResponse get(UUID id);

    ComplianceRuleResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ComplianceRuleResponse> search(ComplianceRuleSearchCriteria criteria, Pageable pageable);

    List<ComplianceRuleSummary> summaries(LifecycleStatus status);

    ComplianceRuleResponse create(ComplianceRuleCreateRequest request);

    ComplianceRuleResponse update(UUID id, ComplianceRuleUpdateRequest request);

    ComplianceRuleResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ComplianceRuleResponse> bulkCreate(List<ComplianceRuleCreateRequest> requests);

    BulkResult<ComplianceRuleResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ComplianceRuleSearchCriteria criteria, Writer writer);

}
