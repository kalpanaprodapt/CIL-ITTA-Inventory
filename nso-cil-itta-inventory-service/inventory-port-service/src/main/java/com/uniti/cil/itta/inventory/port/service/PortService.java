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

/** Application service for Port records. */
public interface PortService {

    PortResponse get(UUID id);

    PortResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<PortResponse> search(PortSearchCriteria criteria, Pageable pageable);

    List<PortSummary> summaries(LifecycleStatus status);

    PortResponse create(PortCreateRequest request);

    PortResponse update(UUID id, PortUpdateRequest request);

    PortResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<PortResponse> bulkCreate(List<PortCreateRequest> requests);

    BulkResult<PortResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(PortSearchCriteria criteria, Writer writer);

    PageResponse<PortResponse> findByDeviceId(UUID deviceId, Pageable pageable);

    PageResponse<PortResponse> findByCardId(UUID cardId, Pageable pageable);
}
