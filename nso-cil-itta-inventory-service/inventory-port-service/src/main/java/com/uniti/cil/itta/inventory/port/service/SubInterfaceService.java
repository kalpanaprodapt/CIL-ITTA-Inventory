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

/** Application service for Sub Interface records. */
public interface SubInterfaceService {

    SubInterfaceResponse get(UUID id);

    SubInterfaceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<SubInterfaceResponse> search(SubInterfaceSearchCriteria criteria, Pageable pageable);

    List<SubInterfaceSummary> summaries(LifecycleStatus status);

    SubInterfaceResponse create(SubInterfaceCreateRequest request);

    SubInterfaceResponse update(UUID id, SubInterfaceUpdateRequest request);

    SubInterfaceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<SubInterfaceResponse> bulkCreate(List<SubInterfaceCreateRequest> requests);

    BulkResult<SubInterfaceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(SubInterfaceSearchCriteria criteria, Writer writer);

    PageResponse<SubInterfaceResponse> findByInterfaceId(UUID interfaceId, Pageable pageable);
}
