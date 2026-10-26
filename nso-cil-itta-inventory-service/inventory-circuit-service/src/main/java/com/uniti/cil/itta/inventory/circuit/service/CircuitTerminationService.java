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

/** Application service for Circuit Termination records. */
public interface CircuitTerminationService {

    CircuitTerminationResponse get(UUID id);

    CircuitTerminationResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CircuitTerminationResponse> search(CircuitTerminationSearchCriteria criteria, Pageable pageable);

    List<CircuitTerminationSummary> summaries(LifecycleStatus status);

    CircuitTerminationResponse create(CircuitTerminationCreateRequest request);

    CircuitTerminationResponse update(UUID id, CircuitTerminationUpdateRequest request);

    CircuitTerminationResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CircuitTerminationResponse> bulkCreate(List<CircuitTerminationCreateRequest> requests);

    BulkResult<CircuitTerminationResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CircuitTerminationSearchCriteria criteria, Writer writer);

    PageResponse<CircuitTerminationResponse> findByCircuitId(UUID circuitId, Pageable pageable);

    PageResponse<CircuitTerminationResponse> findByPortId(UUID portId, Pageable pageable);
}
