package com.uniti.cil.itta.inventory.cabling.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Splice records. */
public interface SpliceService {

    SpliceResponse get(UUID id);

    SpliceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SpliceResponse> search(SpliceSearchCriteria criteria, Pageable pageable);

    List<SpliceSummary> summaries(LifecycleStatus status);

    SpliceResponse create(SpliceCreateRequest request);

    SpliceResponse update(UUID id, SpliceUpdateRequest request);

    SpliceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SpliceResponse> bulkCreate(List<SpliceCreateRequest> requests);

    BulkResult<SpliceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SpliceSearchCriteria criteria, Writer writer);

    PageResponse<SpliceResponse> findByAStrandId(UUID aStrandId, Pageable pageable);

    PageResponse<SpliceResponse> findByZStrandId(UUID zStrandId, Pageable pageable);
}
