package com.uniti.cil.itta.inventory.ipam.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Ip Address records. */
public interface IpAddressService {

    IpAddressResponse get(UUID id);

    IpAddressResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<IpAddressResponse> search(IpAddressSearchCriteria criteria, Pageable pageable);

    List<IpAddressSummary> summaries(LifecycleStatus status);

    IpAddressResponse create(IpAddressCreateRequest request);

    IpAddressResponse update(UUID id, IpAddressUpdateRequest request);

    IpAddressResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<IpAddressResponse> bulkCreate(List<IpAddressCreateRequest> requests);

    BulkResult<IpAddressResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(IpAddressSearchCriteria criteria, Writer writer);

    PageResponse<IpAddressResponse> findBySubnetId(UUID subnetId, Pageable pageable);

    PageResponse<IpAddressResponse> findByAssignedToId(UUID assignedToId, Pageable pageable);
}
