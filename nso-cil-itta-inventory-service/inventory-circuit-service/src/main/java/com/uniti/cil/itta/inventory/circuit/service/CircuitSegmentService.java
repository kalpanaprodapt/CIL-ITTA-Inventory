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

/** Application service for Circuit Segment records. */
public interface CircuitSegmentService {

    CircuitSegmentResponse get(UUID id);

    CircuitSegmentResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CircuitSegmentResponse> search(CircuitSegmentSearchCriteria criteria, Pageable pageable);

    List<CircuitSegmentSummary> summaries(LifecycleStatus status);

    CircuitSegmentResponse create(CircuitSegmentCreateRequest request);

    CircuitSegmentResponse update(UUID id, CircuitSegmentUpdateRequest request);

    CircuitSegmentResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CircuitSegmentResponse> bulkCreate(List<CircuitSegmentCreateRequest> requests);

    BulkResult<CircuitSegmentResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CircuitSegmentSearchCriteria criteria, Writer writer);

    PageResponse<CircuitSegmentResponse> findByCircuitId(UUID circuitId, Pageable pageable);

    PageResponse<CircuitSegmentResponse> findByATerminationId(UUID aTerminationId, Pageable pageable);

    PageResponse<CircuitSegmentResponse> findByZTerminationId(UUID zTerminationId, Pageable pageable);
}
