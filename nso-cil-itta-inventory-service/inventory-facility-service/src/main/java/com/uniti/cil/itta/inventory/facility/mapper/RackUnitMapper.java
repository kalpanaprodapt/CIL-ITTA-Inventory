package com.uniti.cil.itta.inventory.facility.mapper;

import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.RackUnit;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between RackUnit entities and DTOs (kept explicit for auditability). */
@Component
public class RackUnitMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rack_id", "unit_number", "occupied", "mounted_device_id", "face", "source_system", "region", "owner", "criticality", "cost_center");

    public RackUnit toEntity(RackUnitCreateRequest request) {
        RackUnit entity = new RackUnit();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRackId(request.getRackId());
        entity.setUnitNumber(request.getUnitNumber());
        entity.setOccupied(request.getOccupied());
        entity.setMountedDeviceId(request.getMountedDeviceId());
        entity.setFace(request.getFace());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(RackUnitUpdateRequest request, RackUnit entity) {
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
        if (request.getRackId() != null) {
            entity.setRackId(request.getRackId());
        }
        if (request.getUnitNumber() != null) {
            entity.setUnitNumber(request.getUnitNumber());
        }
        if (request.getOccupied() != null) {
            entity.setOccupied(request.getOccupied());
        }
        if (request.getMountedDeviceId() != null) {
            entity.setMountedDeviceId(request.getMountedDeviceId());
        }
        if (request.getFace() != null) {
            entity.setFace(request.getFace());
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
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public RackUnitResponse toResponse(RackUnit entity) {
        RackUnitResponse response = new RackUnitResponse();
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
        response.setRackId(entity.getRackId());
        response.setUnitNumber(entity.getUnitNumber());
        response.setOccupied(entity.getOccupied());
        response.setMountedDeviceId(entity.getMountedDeviceId());
        response.setFace(entity.getFace());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public RackUnitSummary toSummary(RackUnit entity) {
        return new RackUnitSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RackUnitResponse> toResponses(List<RackUnit> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(RackUnit entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRackId(),
                entity.getUnitNumber(),
                entity.getOccupied(),
                entity.getMountedDeviceId(),
                entity.getFace(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
