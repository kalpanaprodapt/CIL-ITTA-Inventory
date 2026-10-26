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

/** Application service for Radio Unit records. */
public interface RadioUnitService {

    RadioUnitResponse get(UUID id);

    RadioUnitResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<RadioUnitResponse> search(RadioUnitSearchCriteria criteria, Pageable pageable);

    List<RadioUnitSummary> summaries(LifecycleStatus status);

    RadioUnitResponse create(RadioUnitCreateRequest request);

    RadioUnitResponse update(UUID id, RadioUnitUpdateRequest request);

    RadioUnitResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<RadioUnitResponse> bulkCreate(List<RadioUnitCreateRequest> requests);

    BulkResult<RadioUnitResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(RadioUnitSearchCriteria criteria, Writer writer);

    PageResponse<RadioUnitResponse> findBySiteId(UUID siteId, Pageable pageable);
}
