package com.uniti.cil.itta.inventory.optical.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Roadm records. */
public interface RoadmService {

    RoadmResponse get(UUID id);

    RoadmResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RoadmResponse> search(RoadmSearchCriteria criteria, Pageable pageable);

    List<RoadmSummary> summaries(LifecycleStatus status);

    RoadmResponse create(RoadmCreateRequest request);

    RoadmResponse update(UUID id, RoadmUpdateRequest request);

    RoadmResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RoadmResponse> bulkCreate(List<RoadmCreateRequest> requests);

    BulkResult<RoadmResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RoadmSearchCriteria criteria, Writer writer);

    PageResponse<RoadmResponse> findBySystemId(UUID systemId, Pageable pageable);
}
