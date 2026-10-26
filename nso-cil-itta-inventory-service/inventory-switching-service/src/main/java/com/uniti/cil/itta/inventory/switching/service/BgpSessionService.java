package com.uniti.cil.itta.inventory.switching.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Bgp Session records. */
public interface BgpSessionService {

    BgpSessionResponse get(UUID id);

    BgpSessionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<BgpSessionResponse> search(BgpSessionSearchCriteria criteria, Pageable pageable);

    List<BgpSessionSummary> summaries(LifecycleStatus status);

    BgpSessionResponse create(BgpSessionCreateRequest request);

    BgpSessionResponse update(UUID id, BgpSessionUpdateRequest request);

    BgpSessionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<BgpSessionResponse> bulkCreate(List<BgpSessionCreateRequest> requests);

    BulkResult<BgpSessionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(BgpSessionSearchCriteria criteria, Writer writer);

    PageResponse<BgpSessionResponse> findByLocalDeviceId(UUID localDeviceId, Pageable pageable);
}
