package com.uniti.cil.itta.inventory.device.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Network Device records. */
public interface NetworkDeviceService {

    NetworkDeviceResponse get(UUID id);

    NetworkDeviceResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<NetworkDeviceResponse> search(NetworkDeviceSearchCriteria criteria, Pageable pageable);

    List<NetworkDeviceSummary> summaries(LifecycleStatus status);

    NetworkDeviceResponse create(NetworkDeviceCreateRequest request);

    NetworkDeviceResponse update(UUID id, NetworkDeviceUpdateRequest request);

    NetworkDeviceResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<NetworkDeviceResponse> bulkCreate(List<NetworkDeviceCreateRequest> requests);

    BulkResult<NetworkDeviceResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(NetworkDeviceSearchCriteria criteria, Writer writer);

    PageResponse<NetworkDeviceResponse> findByModelId(UUID modelId, Pageable pageable);

    PageResponse<NetworkDeviceResponse> findByRackUnitId(UUID rackUnitId, Pageable pageable);
}
