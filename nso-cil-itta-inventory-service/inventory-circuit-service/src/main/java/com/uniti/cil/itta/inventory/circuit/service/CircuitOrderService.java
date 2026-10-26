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

/** Application service for Circuit Order records. */
public interface CircuitOrderService {

    CircuitOrderResponse get(UUID id);

    CircuitOrderResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CircuitOrderResponse> search(CircuitOrderSearchCriteria criteria, Pageable pageable);

    List<CircuitOrderSummary> summaries(LifecycleStatus status);

    CircuitOrderResponse create(CircuitOrderCreateRequest request);

    CircuitOrderResponse update(UUID id, CircuitOrderUpdateRequest request);

    CircuitOrderResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CircuitOrderResponse> bulkCreate(List<CircuitOrderCreateRequest> requests);

    BulkResult<CircuitOrderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CircuitOrderSearchCriteria criteria, Writer writer);

    PageResponse<CircuitOrderResponse> findByCircuitId(UUID circuitId, Pageable pageable);
}
