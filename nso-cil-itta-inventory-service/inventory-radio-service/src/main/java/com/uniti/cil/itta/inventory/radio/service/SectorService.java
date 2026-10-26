package com.uniti.cil.itta.inventory.radio.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Sector records. */
public interface SectorService {

    SectorResponse get(UUID id);

    SectorResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SectorResponse> search(SectorSearchCriteria criteria, Pageable pageable);

    List<SectorSummary> summaries(LifecycleStatus status);

    SectorResponse create(SectorCreateRequest request);

    SectorResponse update(UUID id, SectorUpdateRequest request);

    SectorResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SectorResponse> bulkCreate(List<SectorCreateRequest> requests);

    BulkResult<SectorResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SectorSearchCriteria criteria, Writer writer);

    PageResponse<SectorResponse> findByBaseStationId(UUID baseStationId, Pageable pageable);
}
