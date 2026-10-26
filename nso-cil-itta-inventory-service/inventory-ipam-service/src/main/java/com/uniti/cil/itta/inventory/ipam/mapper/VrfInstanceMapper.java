package com.uniti.cil.itta.inventory.ipam.mapper;

import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.VrfInstance;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between VrfInstance entities and DTOs (kept explicit for auditability). */
@Component
public class VrfInstanceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vrf_name", "route_distinguisher", "import_targets", "export_targets", "device_id", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public VrfInstance toEntity(VrfInstanceCreateRequest request) {
        VrfInstance entity = new VrfInstance();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVrfName(request.getVrfName());
        entity.setRouteDistinguisher(request.getRouteDistinguisher());
        entity.setImportTargets(request.getImportTargets());
        entity.setExportTargets(request.getExportTargets());
        entity.setDeviceId(request.getDeviceId());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(VrfInstanceUpdateRequest request, VrfInstance entity) {
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
        if (request.getVrfName() != null) {
            entity.setVrfName(request.getVrfName());
        }
        if (request.getRouteDistinguisher() != null) {
            entity.setRouteDistinguisher(request.getRouteDistinguisher());
        }
        if (request.getImportTargets() != null) {
            entity.setImportTargets(request.getImportTargets());
        }
        if (request.getExportTargets() != null) {
            entity.setExportTargets(request.getExportTargets());
        }
        if (request.getDeviceId() != null) {
            entity.setDeviceId(request.getDeviceId());
        }
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
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
    }

    public VrfInstanceResponse toResponse(VrfInstance entity) {
        VrfInstanceResponse response = new VrfInstanceResponse();
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
        response.setVrfName(entity.getVrfName());
        response.setRouteDistinguisher(entity.getRouteDistinguisher());
        response.setImportTargets(entity.getImportTargets());
        response.setExportTargets(entity.getExportTargets());
        response.setDeviceId(entity.getDeviceId());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public VrfInstanceSummary toSummary(VrfInstance entity) {
        return new VrfInstanceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VrfInstanceResponse> toResponses(List<VrfInstance> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(VrfInstance entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVrfName(),
                entity.getRouteDistinguisher(),
                entity.getImportTargets(),
                entity.getExportTargets(),
                entity.getDeviceId(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
