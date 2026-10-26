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

/** Application service for Optical Channel records. */
public interface OpticalChannelService {

    OpticalChannelResponse get(UUID id);

    OpticalChannelResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<OpticalChannelResponse> search(OpticalChannelSearchCriteria criteria, Pageable pageable);

    List<OpticalChannelSummary> summaries(LifecycleStatus status);

    OpticalChannelResponse create(OpticalChannelCreateRequest request);

    OpticalChannelResponse update(UUID id, OpticalChannelUpdateRequest request);

    OpticalChannelResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<OpticalChannelResponse> bulkCreate(List<OpticalChannelCreateRequest> requests);

    BulkResult<OpticalChannelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(OpticalChannelSearchCriteria criteria, Writer writer);

    PageResponse<OpticalChannelResponse> findBySystemId(UUID systemId, Pageable pageable);
}
