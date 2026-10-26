package com.uniti.cil.itta.inventory.discovery.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Discovery Job records. */
public interface DiscoveryJobService {

    DiscoveryJobResponse get(UUID id);

    DiscoveryJobResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<DiscoveryJobResponse> search(DiscoveryJobSearchCriteria criteria, Pageable pageable);

    List<DiscoveryJobSummary> summaries(LifecycleStatus status);

    DiscoveryJobResponse create(DiscoveryJobCreateRequest request);

    DiscoveryJobResponse update(UUID id, DiscoveryJobUpdateRequest request);

    DiscoveryJobResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<DiscoveryJobResponse> bulkCreate(List<DiscoveryJobCreateRequest> requests);

    BulkResult<DiscoveryJobResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(DiscoveryJobSearchCriteria criteria, Writer writer);

    PageResponse<DiscoveryJobResponse> findByProfileId(UUID profileId, Pageable pageable);
}
