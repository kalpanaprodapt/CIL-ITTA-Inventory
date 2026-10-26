package com.uniti.cil.itta.inventory.virtual.mapper;

import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.Vnf;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Vnf entities and DTOs (kept explicit for auditability). */
@Component
public class VnfMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vnf_name", "vnf_type", "flavor", "vcpu", "memory_gb", "instance_count", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public Vnf toEntity(VnfCreateRequest request) {
        Vnf entity = new Vnf();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVnfName(request.getVnfName());
        entity.setVnfType(request.getVnfType());
        entity.setFlavor(request.getFlavor());
        entity.setVcpu(request.getVcpu());
        entity.setMemoryGb(request.getMemoryGb());
        entity.setInstanceCount(request.getInstanceCount());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(VnfUpdateRequest request, Vnf entity) {
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
        if (request.getVnfName() != null) {
            entity.setVnfName(request.getVnfName());
        }
        if (request.getVnfType() != null) {
            entity.setVnfType(request.getVnfType());
        }
        if (request.getFlavor() != null) {
            entity.setFlavor(request.getFlavor());
        }
        if (request.getVcpu() != null) {
            entity.setVcpu(request.getVcpu());
        }
        if (request.getMemoryGb() != null) {
            entity.setMemoryGb(request.getMemoryGb());
        }
        if (request.getInstanceCount() != null) {
            entity.setInstanceCount(request.getInstanceCount());
        }
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
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
    }

    public VnfResponse toResponse(Vnf entity) {
        VnfResponse response = new VnfResponse();
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
        response.setVnfName(entity.getVnfName());
        response.setVnfType(entity.getVnfType());
        response.setFlavor(entity.getFlavor());
        response.setVcpu(entity.getVcpu());
        response.setMemoryGb(entity.getMemoryGb());
        response.setInstanceCount(entity.getInstanceCount());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public VnfSummary toSummary(Vnf entity) {
        return new VnfSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VnfResponse> toResponses(List<Vnf> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Vnf entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVnfName(),
                entity.getVnfType(),
                entity.getFlavor(),
                entity.getVcpu(),
                entity.getMemoryGb(),
                entity.getInstanceCount(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
