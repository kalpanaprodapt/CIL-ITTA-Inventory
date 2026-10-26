package com.uniti.cil.itta.inventory.mpls.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Mpls Lsp records. */
public interface MplsLspService {

    MplsLspResponse get(UUID id);

    MplsLspResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<MplsLspResponse> search(MplsLspSearchCriteria criteria, Pageable pageable);

    List<MplsLspSummary> summaries(LifecycleStatus status);

    MplsLspResponse create(MplsLspCreateRequest request);

    MplsLspResponse update(UUID id, MplsLspUpdateRequest request);

    MplsLspResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<MplsLspResponse> bulkCreate(List<MplsLspCreateRequest> requests);

    BulkResult<MplsLspResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(MplsLspSearchCriteria criteria, Writer writer);

    PageResponse<MplsLspResponse> findByHeadEndId(UUID headEndId, Pageable pageable);

    PageResponse<MplsLspResponse> findByTailEndId(UUID tailEndId, Pageable pageable);
}
