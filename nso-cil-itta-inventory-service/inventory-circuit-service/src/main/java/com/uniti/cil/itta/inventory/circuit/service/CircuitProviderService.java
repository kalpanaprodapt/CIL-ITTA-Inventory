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

/** Application service for Circuit Provider records. */
public interface CircuitProviderService {

    CircuitProviderResponse get(UUID id);

    CircuitProviderResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<CircuitProviderResponse> search(CircuitProviderSearchCriteria criteria, Pageable pageable);

    List<CircuitProviderSummary> summaries(LifecycleStatus status);

    CircuitProviderResponse create(CircuitProviderCreateRequest request);

    CircuitProviderResponse update(UUID id, CircuitProviderUpdateRequest request);

    CircuitProviderResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<CircuitProviderResponse> bulkCreate(List<CircuitProviderCreateRequest> requests);

    BulkResult<CircuitProviderResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(CircuitProviderSearchCriteria criteria, Writer writer);

}
