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

/** Application service for Compliance Check records. */
public interface ComplianceCheckService {

    ComplianceCheckResponse get(UUID id);

    ComplianceCheckResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ComplianceCheckResponse> search(ComplianceCheckSearchCriteria criteria, Pageable pageable);

    List<ComplianceCheckSummary> summaries(LifecycleStatus status);

    ComplianceCheckResponse create(ComplianceCheckCreateRequest request);

    ComplianceCheckResponse update(UUID id, ComplianceCheckUpdateRequest request);

    ComplianceCheckResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ComplianceCheckResponse> bulkCreate(List<ComplianceCheckCreateRequest> requests);

    BulkResult<ComplianceCheckResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ComplianceCheckSearchCriteria criteria, Writer writer);

    PageResponse<ComplianceCheckResponse> findByRuleId(UUID ruleId, Pageable pageable);
}
