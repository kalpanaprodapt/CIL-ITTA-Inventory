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

/** Application service for Ip Pool records. */
public interface IpPoolService {

    IpPoolResponse get(UUID id);

    IpPoolResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<IpPoolResponse> search(IpPoolSearchCriteria criteria, Pageable pageable);

    List<IpPoolSummary> summaries(LifecycleStatus status);

    IpPoolResponse create(IpPoolCreateRequest request);

    IpPoolResponse update(UUID id, IpPoolUpdateRequest request);

    IpPoolResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<IpPoolResponse> bulkCreate(List<IpPoolCreateRequest> requests);

    BulkResult<IpPoolResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(IpPoolSearchCriteria criteria, Writer writer);

    PageResponse<IpPoolResponse> findBySubnetId(UUID subnetId, Pageable pageable);
}
