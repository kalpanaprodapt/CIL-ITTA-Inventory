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

/** Application service for Pseudo Wire records. */
public interface PseudoWireService {

    PseudoWireResponse get(UUID id);

    PseudoWireResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PseudoWireResponse> search(PseudoWireSearchCriteria criteria, Pageable pageable);

    List<PseudoWireSummary> summaries(LifecycleStatus status);

    PseudoWireResponse create(PseudoWireCreateRequest request);

    PseudoWireResponse update(UUID id, PseudoWireUpdateRequest request);

    PseudoWireResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PseudoWireResponse> bulkCreate(List<PseudoWireCreateRequest> requests);

    BulkResult<PseudoWireResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PseudoWireSearchCriteria criteria, Writer writer);

    PageResponse<PseudoWireResponse> findByAEndId(UUID aEndId, Pageable pageable);

    PageResponse<PseudoWireResponse> findByZEndId(UUID zEndId, Pageable pageable);
}
