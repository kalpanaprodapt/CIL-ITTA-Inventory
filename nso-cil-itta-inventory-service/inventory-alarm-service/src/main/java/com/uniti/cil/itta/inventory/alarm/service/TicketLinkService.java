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

/** Application service for Ticket Link records. */
public interface TicketLinkService {

    TicketLinkResponse get(UUID id);

    TicketLinkResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<TicketLinkResponse> search(TicketLinkSearchCriteria criteria, Pageable pageable);

    List<TicketLinkSummary> summaries(LifecycleStatus status);

    TicketLinkResponse create(TicketLinkCreateRequest request);

    TicketLinkResponse update(UUID id, TicketLinkUpdateRequest request);

    TicketLinkResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<TicketLinkResponse> bulkCreate(List<TicketLinkCreateRequest> requests);

    BulkResult<TicketLinkResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(TicketLinkSearchCriteria criteria, Writer writer);

    PageResponse<TicketLinkResponse> findByAlarmId(UUID alarmId, Pageable pageable);
}
