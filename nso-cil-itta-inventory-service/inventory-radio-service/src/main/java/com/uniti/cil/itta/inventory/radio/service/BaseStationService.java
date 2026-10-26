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

/** Application service for Base Station records. */
public interface BaseStationService {

    BaseStationResponse get(UUID id);

    BaseStationResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<BaseStationResponse> search(BaseStationSearchCriteria criteria, Pageable pageable);

    List<BaseStationSummary> summaries(LifecycleStatus status);

    BaseStationResponse create(BaseStationCreateRequest request);

    BaseStationResponse update(UUID id, BaseStationUpdateRequest request);

    BaseStationResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<BaseStationResponse> bulkCreate(List<BaseStationCreateRequest> requests);

    BulkResult<BaseStationResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(BaseStationSearchCriteria criteria, Writer writer);

    PageResponse<BaseStationResponse> findBySiteId(UUID siteId, Pageable pageable);
}
