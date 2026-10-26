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

/** Application service for Ip Subnet records. */
public interface IpSubnetService {

    IpSubnetResponse get(UUID id);

    IpSubnetResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<IpSubnetResponse> search(IpSubnetSearchCriteria criteria, Pageable pageable);

    List<IpSubnetSummary> summaries(LifecycleStatus status);

    IpSubnetResponse create(IpSubnetCreateRequest request);

    IpSubnetResponse update(UUID id, IpSubnetUpdateRequest request);

    IpSubnetResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<IpSubnetResponse> bulkCreate(List<IpSubnetCreateRequest> requests);

    BulkResult<IpSubnetResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(IpSubnetSearchCriteria criteria, Writer writer);

    PageResponse<IpSubnetResponse> findByVrfId(UUID vrfId, Pageable pageable);

    PageResponse<IpSubnetResponse> findByVlanId(UUID vlanId, Pageable pageable);
}
