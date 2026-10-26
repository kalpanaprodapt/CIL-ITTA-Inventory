package com.uniti.cil.itta.inventory.integration.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Import Batch records. */
public interface ImportBatchService {

    ImportBatchResponse get(UUID id);

    ImportBatchResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ImportBatchResponse> search(ImportBatchSearchCriteria criteria, Pageable pageable);

    List<ImportBatchSummary> summaries(LifecycleStatus status);

    ImportBatchResponse create(ImportBatchCreateRequest request);

    ImportBatchResponse update(UUID id, ImportBatchUpdateRequest request);

    ImportBatchResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ImportBatchResponse> bulkCreate(List<ImportBatchCreateRequest> requests);

    BulkResult<ImportBatchResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ImportBatchSearchCriteria criteria, Writer writer);

}
