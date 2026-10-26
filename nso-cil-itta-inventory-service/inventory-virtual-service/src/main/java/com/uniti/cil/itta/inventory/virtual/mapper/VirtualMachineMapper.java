package com.uniti.cil.itta.inventory.virtual.mapper;

import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.VirtualMachine;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between VirtualMachine entities and DTOs (kept explicit for auditability). */
@Component
public class VirtualMachineMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "hypervisor_id", "vm_name", "vcpu", "memory_gb", "disk_gb", "power_state", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public VirtualMachine toEntity(VirtualMachineCreateRequest request) {
        VirtualMachine entity = new VirtualMachine();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setHypervisorId(request.getHypervisorId());
        entity.setVmName(request.getVmName());
        entity.setVcpu(request.getVcpu());
        entity.setMemoryGb(request.getMemoryGb());
        entity.setDiskGb(request.getDiskGb());
        entity.setPowerState(request.getPowerState());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(VirtualMachineUpdateRequest request, VirtualMachine entity) {
        if (request.getCode() != null) {
            entity.setCode(request.getCode());
        }
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getExternalRef() != null) {
            entity.setExternalRef(request.getExternalRef());
        }
        if (request.getHypervisorId() != null) {
            entity.setHypervisorId(request.getHypervisorId());
        }
        if (request.getVmName() != null) {
            entity.setVmName(request.getVmName());
        }
        if (request.getVcpu() != null) {
            entity.setVcpu(request.getVcpu());
        }
        if (request.getMemoryGb() != null) {
            entity.setMemoryGb(request.getMemoryGb());
        }
        if (request.getDiskGb() != null) {
            entity.setDiskGb(request.getDiskGb());
        }
        if (request.getPowerState() != null) {
            entity.setPowerState(request.getPowerState());
        }
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
    }

    public VirtualMachineResponse toResponse(VirtualMachine entity) {
        VirtualMachineResponse response = new VirtualMachineResponse();
        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setVersion(entity.getVersion());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        response.setExternalRef(entity.getExternalRef());
        response.setHypervisorId(entity.getHypervisorId());
        response.setVmName(entity.getVmName());
        response.setVcpu(entity.getVcpu());
        response.setMemoryGb(entity.getMemoryGb());
        response.setDiskGb(entity.getDiskGb());
        response.setPowerState(entity.getPowerState());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public VirtualMachineSummary toSummary(VirtualMachine entity) {
        return new VirtualMachineSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VirtualMachineResponse> toResponses(List<VirtualMachine> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(VirtualMachine entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getHypervisorId(),
                entity.getVmName(),
                entity.getVcpu(),
                entity.getMemoryGb(),
                entity.getDiskGb(),
                entity.getPowerState(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
