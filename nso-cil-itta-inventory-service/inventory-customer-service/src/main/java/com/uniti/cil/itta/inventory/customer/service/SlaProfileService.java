package com.uniti.cil.itta.inventory.customer.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Sla Profile records. */
public interface SlaProfileService {

    SlaProfileResponse get(UUID id);

    SlaProfileResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SlaProfileResponse> search(SlaProfileSearchCriteria criteria, Pageable pageable);

    List<SlaProfileSummary> summaries(LifecycleStatus status);

    SlaProfileResponse create(SlaProfileCreateRequest request);

    SlaProfileResponse update(UUID id, SlaProfileUpdateRequest request);

    SlaProfileResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SlaProfileResponse> bulkCreate(List<SlaProfileCreateRequest> requests);

    BulkResult<SlaProfileResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SlaProfileSearchCriteria criteria, Writer writer);

}
