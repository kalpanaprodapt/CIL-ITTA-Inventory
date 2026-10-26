package com.uniti.cil.itta.inventory.virtual.mapper;

import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.Hypervisor;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Hypervisor entities and DTOs (kept explicit for auditability). */
@Component
public class HypervisorMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "host_device_id", "hypervisor_type", "hv_version", "vcpu_capacity", "memory_gb_capacity", "region", "owner", "criticality", "cost_center", "notes");

    public Hypervisor toEntity(HypervisorCreateRequest request) {
        Hypervisor entity = new Hypervisor();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setHostDeviceId(request.getHostDeviceId());
        entity.setHypervisorType(request.getHypervisorType());
        entity.setHvVersion(request.getHvVersion());
        entity.setVcpuCapacity(request.getVcpuCapacity());
        entity.setMemoryGbCapacity(request.getMemoryGbCapacity());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(HypervisorUpdateRequest request, Hypervisor entity) {
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
        if (request.getHostDeviceId() != null) {
            entity.setHostDeviceId(request.getHostDeviceId());
        }
        if (request.getHypervisorType() != null) {
            entity.setHypervisorType(request.getHypervisorType());
        }
        if (request.getHvVersion() != null) {
            entity.setHvVersion(request.getHvVersion());
        }
        if (request.getVcpuCapacity() != null) {
            entity.setVcpuCapacity(request.getVcpuCapacity());
        }
        if (request.getMemoryGbCapacity() != null) {
            entity.setMemoryGbCapacity(request.getMemoryGbCapacity());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
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
    }

    public HypervisorResponse toResponse(Hypervisor entity) {
        HypervisorResponse response = new HypervisorResponse();
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
        response.setHostDeviceId(entity.getHostDeviceId());
        response.setHypervisorType(entity.getHypervisorType());
        response.setHvVersion(entity.getHvVersion());
        response.setVcpuCapacity(entity.getVcpuCapacity());
        response.setMemoryGbCapacity(entity.getMemoryGbCapacity());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public HypervisorSummary toSummary(Hypervisor entity) {
        return new HypervisorSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<HypervisorResponse> toResponses(List<Hypervisor> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Hypervisor entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getHostDeviceId(),
                entity.getHypervisorType(),
                entity.getHvVersion(),
                entity.getVcpuCapacity(),
                entity.getMemoryGbCapacity(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
