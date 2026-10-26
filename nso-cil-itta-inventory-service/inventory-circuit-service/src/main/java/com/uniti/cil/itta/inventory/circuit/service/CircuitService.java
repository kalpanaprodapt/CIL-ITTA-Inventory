package com.uniti.cil.itta.inventory.circuit.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Circuit records. */
public interface CircuitService {

    CircuitResponse get(UUID id);

    CircuitResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CircuitResponse> search(CircuitSearchCriteria criteria, Pageable pageable);

    List<CircuitSummary> summaries(LifecycleStatus status);

    CircuitResponse create(CircuitCreateRequest request);

    CircuitResponse update(UUID id, CircuitUpdateRequest request);

    CircuitResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CircuitResponse> bulkCreate(List<CircuitCreateRequest> requests);

    BulkResult<CircuitResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CircuitSearchCriteria criteria, Writer writer);

    PageResponse<CircuitResponse> findByProviderId(UUID providerId, Pageable pageable);

    PageResponse<CircuitResponse> findByASiteId(UUID aSiteId, Pageable pageable);

    PageResponse<CircuitResponse> findByZSiteId(UUID zSiteId, Pageable pageable);
}
