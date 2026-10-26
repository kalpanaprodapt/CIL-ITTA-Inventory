package com.uniti.cil.itta.inventory.alarm.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Event Source records. */
public interface EventSourceService {

    EventSourceResponse get(UUID id);

    EventSourceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<EventSourceResponse> search(EventSourceSearchCriteria criteria, Pageable pageable);

    List<EventSourceSummary> summaries(LifecycleStatus status);

    EventSourceResponse create(EventSourceCreateRequest request);

    EventSourceResponse update(UUID id, EventSourceUpdateRequest request);

    EventSourceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<EventSourceResponse> bulkCreate(List<EventSourceCreateRequest> requests);

    BulkResult<EventSourceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(EventSourceSearchCriteria criteria, Writer writer);

}
