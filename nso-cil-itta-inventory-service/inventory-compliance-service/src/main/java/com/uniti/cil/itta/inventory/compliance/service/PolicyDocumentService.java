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

/** Application service for Policy Document records. */
public interface PolicyDocumentService {

    PolicyDocumentResponse get(UUID id);

    PolicyDocumentResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PolicyDocumentResponse> search(PolicyDocumentSearchCriteria criteria, Pageable pageable);

    List<PolicyDocumentSummary> summaries(LifecycleStatus status);

    PolicyDocumentResponse create(PolicyDocumentCreateRequest request);

    PolicyDocumentResponse update(UUID id, PolicyDocumentUpdateRequest request);

    PolicyDocumentResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PolicyDocumentResponse> bulkCreate(List<PolicyDocumentCreateRequest> requests);

    BulkResult<PolicyDocumentResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PolicyDocumentSearchCriteria criteria, Writer writer);

}
