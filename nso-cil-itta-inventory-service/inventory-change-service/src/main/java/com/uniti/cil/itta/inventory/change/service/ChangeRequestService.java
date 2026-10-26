package com.uniti.cil.itta.inventory.change.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Change Request records. */
public interface ChangeRequestService {

    ChangeRequestResponse get(UUID id);

    ChangeRequestResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ChangeRequestResponse> search(ChangeRequestSearchCriteria criteria, Pageable pageable);

    List<ChangeRequestSummary> summaries(LifecycleStatus status);

    ChangeRequestResponse create(ChangeRequestCreateRequest request);

    ChangeRequestResponse update(UUID id, ChangeRequestUpdateRequest request);

    ChangeRequestResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ChangeRequestResponse> bulkCreate(List<ChangeRequestCreateRequest> requests);

    BulkResult<ChangeRequestResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ChangeRequestSearchCriteria criteria, Writer writer);

}
