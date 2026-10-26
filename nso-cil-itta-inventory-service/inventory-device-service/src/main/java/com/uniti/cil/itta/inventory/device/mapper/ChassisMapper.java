package com.uniti.cil.itta.inventory.device.mapper;

import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.Chassis;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Chassis entities and DTOs (kept explicit for auditability). */
@Component
public class ChassisMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "device_id", "slot_count", "serial_number", "backplane_gbps", "redundancy_mode", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public Chassis toEntity(ChassisCreateRequest request) {
        Chassis entity = new Chassis();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setDeviceId(request.getDeviceId());
        entity.setSlotCount(request.getSlotCount());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setBackplaneGbps(request.getBackplaneGbps());
        entity.setRedundancyMode(request.getRedundancyMode());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(ChassisUpdateRequest request, Chassis entity) {
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
        if (request.getDeviceId() != null) {
            entity.setDeviceId(request.getDeviceId());
        }
        if (request.getSlotCount() != null) {
            entity.setSlotCount(request.getSlotCount());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
        }
        if (request.getBackplaneGbps() != null) {
            entity.setBackplaneGbps(request.getBackplaneGbps());
        }
        if (request.getRedundancyMode() != null) {
            entity.setRedundancyMode(request.getRedundancyMode());
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

    public ChassisResponse toResponse(Chassis entity) {
        ChassisResponse response = new ChassisResponse();
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
        response.setDeviceId(entity.getDeviceId());
        response.setSlotCount(entity.getSlotCount());
        response.setSerialNumber(entity.getSerialNumber());
        response.setBackplaneGbps(entity.getBackplaneGbps());
        response.setRedundancyMode(entity.getRedundancyMode());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public ChassisSummary toSummary(Chassis entity) {
        return new ChassisSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ChassisResponse> toResponses(List<Chassis> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Chassis entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getDeviceId(),
                entity.getSlotCount(),
                entity.getSerialNumber(),
                entity.getBackplaneGbps(),
                entity.getRedundancyMode(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
