package com.uniti.cil.itta.inventory.assetmgmt.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Invoice records. */
public interface InvoiceService {

    InvoiceResponse get(UUID id);

    InvoiceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<InvoiceResponse> search(InvoiceSearchCriteria criteria, Pageable pageable);

    List<InvoiceSummary> summaries(LifecycleStatus status);

    InvoiceResponse create(InvoiceCreateRequest request);

    InvoiceResponse update(UUID id, InvoiceUpdateRequest request);

    InvoiceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<InvoiceResponse> bulkCreate(List<InvoiceCreateRequest> requests);

    BulkResult<InvoiceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(InvoiceSearchCriteria criteria, Writer writer);

    PageResponse<InvoiceResponse> findByPoId(UUID poId, Pageable pageable);
}
