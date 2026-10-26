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

/** Application service for Discovery Profile records. */
public interface DiscoveryProfileService {

    DiscoveryProfileResponse get(UUID id);

    DiscoveryProfileResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<DiscoveryProfileResponse> search(DiscoveryProfileSearchCriteria criteria, Pageable pageable);

    List<DiscoveryProfileSummary> summaries(LifecycleStatus status);

    DiscoveryProfileResponse create(DiscoveryProfileCreateRequest request);

    DiscoveryProfileResponse update(UUID id, DiscoveryProfileUpdateRequest request);

    DiscoveryProfileResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<DiscoveryProfileResponse> bulkCreate(List<DiscoveryProfileCreateRequest> requests);

    BulkResult<DiscoveryProfileResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(DiscoveryProfileSearchCriteria criteria, Writer writer);

}
