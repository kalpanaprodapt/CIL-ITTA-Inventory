package com.uniti.cil.itta.inventory.geography.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for City records. */
public interface CityService {

    CityResponse get(UUID id);

    CityResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CityResponse> search(CitySearchCriteria criteria, Pageable pageable);

    List<CitySummary> summaries(LifecycleStatus status);

    CityResponse create(CityCreateRequest request);

    CityResponse update(UUID id, CityUpdateRequest request);

    CityResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CityResponse> bulkCreate(List<CityCreateRequest> requests);

    BulkResult<CityResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CitySearchCriteria criteria, Writer writer);

    PageResponse<CityResponse> findByRegionId(UUID regionId, Pageable pageable);
}
