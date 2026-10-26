package com.uniti.cil.itta.inventory.capacity.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Forecast Model records. */
public interface ForecastModelService {

    ForecastModelResponse get(UUID id);

    ForecastModelResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<ForecastModelResponse> search(ForecastModelSearchCriteria criteria, Pageable pageable);

    List<ForecastModelSummary> summaries(LifecycleStatus status);

    ForecastModelResponse create(ForecastModelCreateRequest request);

    ForecastModelResponse update(UUID id, ForecastModelUpdateRequest request);

    ForecastModelResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<ForecastModelResponse> bulkCreate(List<ForecastModelCreateRequest> requests);

    BulkResult<ForecastModelResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(ForecastModelSearchCriteria criteria, Writer writer);

}
