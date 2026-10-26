package com.uniti.cil.itta.inventory.virtual.service;

import com.uniti.cil.itta.inventory.common.dto.BulkResult;
import com.uniti.cil.itta.inventory.common.dto.PageResponse;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/** Application service for Virtual Machine records. */
public interface VirtualMachineService {

    VirtualMachineResponse get(UUID id);

    VirtualMachineResponse getByCode(String code);

    boolean existsByCode(String code);

    PageResponse<VirtualMachineResponse> search(VirtualMachineSearchCriteria criteria, Pageable pageable);

    List<VirtualMachineSummary> summaries(LifecycleStatus status);

    VirtualMachineResponse create(VirtualMachineCreateRequest request);

    VirtualMachineResponse update(UUID id, VirtualMachineUpdateRequest request);

    VirtualMachineResponse changeStatus(UUID id, LifecycleStatus status);

    void delete(UUID id);

    BulkResult<VirtualMachineResponse> bulkCreate(List<VirtualMachineCreateRequest> requests);

    BulkResult<VirtualMachineResponse> bulkChangeStatus(List<UUID> ids, LifecycleStatus status);

    Map<LifecycleStatus, Long> countByStatus();

    void exportCsv(VirtualMachineSearchCriteria criteria, Writer writer);

    PageResponse<VirtualMachineResponse> findByHypervisorId(UUID hypervisorId, Pageable pageable);
}
