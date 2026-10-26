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

/** Application service for Compliance Exception records. */
public interface ComplianceExceptionService {

    ComplianceExceptionResponse get(UUID id);

    ComplianceExceptionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ComplianceExceptionResponse> search(ComplianceExceptionSearchCriteria criteria, Pageable pageable);

    List<ComplianceExceptionSummary> summaries(LifecycleStatus status);

    ComplianceExceptionResponse create(ComplianceExceptionCreateRequest request);

    ComplianceExceptionResponse update(UUID id, ComplianceExceptionUpdateRequest request);

    ComplianceExceptionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ComplianceExceptionResponse> bulkCreate(List<ComplianceExceptionCreateRequest> requests);

    BulkResult<ComplianceExceptionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ComplianceExceptionSearchCriteria criteria, Writer writer);

    PageResponse<ComplianceExceptionResponse> findByRuleId(UUID ruleId, Pageable pageable);
}
