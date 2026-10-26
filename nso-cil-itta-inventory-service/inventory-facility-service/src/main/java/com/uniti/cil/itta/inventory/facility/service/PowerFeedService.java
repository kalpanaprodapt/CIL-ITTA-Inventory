package com.uniti.cil.itta.inventory.facility.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Power Feed records. */
public interface PowerFeedService {

    PowerFeedResponse get(UUID id);

    PowerFeedResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PowerFeedResponse> search(PowerFeedSearchCriteria criteria, Pageable pageable);

    List<PowerFeedSummary> summaries(LifecycleStatus status);

    PowerFeedResponse create(PowerFeedCreateRequest request);

    PowerFeedResponse update(UUID id, PowerFeedUpdateRequest request);

    PowerFeedResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PowerFeedResponse> bulkCreate(List<PowerFeedCreateRequest> requests);

    BulkResult<PowerFeedResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PowerFeedSearchCriteria criteria, Writer writer);

    PageResponse<PowerFeedResponse> findByRoomId(UUID roomId, Pageable pageable);
}
