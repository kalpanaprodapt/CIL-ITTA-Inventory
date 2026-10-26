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

/** Application service for Audit Record records. */
public interface AuditRecordService {

    AuditRecordResponse get(UUID id);

    AuditRecordResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<AuditRecordResponse> search(AuditRecordSearchCriteria criteria, Pageable pageable);

    List<AuditRecordSummary> summaries(LifecycleStatus status);

    AuditRecordResponse create(AuditRecordCreateRequest request);

    AuditRecordResponse update(UUID id, AuditRecordUpdateRequest request);

    AuditRecordResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<AuditRecordResponse> bulkCreate(List<AuditRecordCreateRequest> requests);

    BulkResult<AuditRecordResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(AuditRecordSearchCriteria criteria, Writer writer);

}
