package com.uniti.cil.itta.inventory.device.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Line Card records. */
public interface LineCardService {

    LineCardResponse get(UUID id);

    LineCardResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<LineCardResponse> search(LineCardSearchCriteria criteria, Pageable pageable);

    List<LineCardSummary> summaries(LifecycleStatus status);

    LineCardResponse create(LineCardCreateRequest request);

    LineCardResponse update(UUID id, LineCardUpdateRequest request);

    LineCardResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<LineCardResponse> bulkCreate(List<LineCardCreateRequest> requests);

    BulkResult<LineCardResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(LineCardSearchCriteria criteria, Writer writer);

    PageResponse<LineCardResponse> findByChassisId(UUID chassisId, Pageable pageable);
}
