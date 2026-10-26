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

/** Application service for Region records. */
public interface RegionService {

    RegionResponse get(UUID id);

    RegionResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RegionResponse> search(RegionSearchCriteria criteria, Pageable pageable);

    List<RegionSummary> summaries(LifecycleStatus status);

    RegionResponse create(RegionCreateRequest request);

    RegionResponse update(UUID id, RegionUpdateRequest request);

    RegionResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RegionResponse> bulkCreate(List<RegionCreateRequest> requests);

    BulkResult<RegionResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RegionSearchCriteria criteria, Writer writer);

    PageResponse<RegionResponse> findByCountryId(UUID countryId, Pageable pageable);
}
