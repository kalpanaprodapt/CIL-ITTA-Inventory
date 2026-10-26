package com.uniti.cil.itta.inventory.port.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Interface records. */
public interface InterfaceService {

    InterfaceResponse get(UUID id);

    InterfaceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<InterfaceResponse> search(InterfaceSearchCriteria criteria, Pageable pageable);

    List<InterfaceSummary> summaries(LifecycleStatus status);

    InterfaceResponse create(InterfaceCreateRequest request);

    InterfaceResponse update(UUID id, InterfaceUpdateRequest request);

    InterfaceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<InterfaceResponse> bulkCreate(List<InterfaceCreateRequest> requests);

    BulkResult<InterfaceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(InterfaceSearchCriteria criteria, Writer writer);

    PageResponse<InterfaceResponse> findByPortId(UUID portId, Pageable pageable);
}
