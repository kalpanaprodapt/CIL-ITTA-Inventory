package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Reservation records. */
public interface ReservationService {

    ReservationResponse get(UUID id);

    ReservationResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ReservationResponse> search(ReservationSearchCriteria criteria, Pageable pageable);

    List<ReservationSummary> summaries(LifecycleStatus status);

    ReservationResponse create(ReservationCreateRequest request);

    ReservationResponse update(UUID id, ReservationUpdateRequest request);

    ReservationResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ReservationResponse> bulkCreate(List<ReservationCreateRequest> requests);

    BulkResult<ReservationResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ReservationSearchCriteria criteria, Writer writer);

}
