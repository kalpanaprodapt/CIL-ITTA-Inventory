package com.uniti.cil.itta.inventory.facility.mapper;

import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.Rack;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Rack entities and DTOs (kept explicit for auditability). */
@Component
public class RackMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "room_id", "rack_units", "width_mm", "depth_mm", "max_weight_kg", "row_label", "position", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public Rack toEntity(RackCreateRequest request) {
        Rack entity = new Rack();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRoomId(request.getRoomId());
        entity.setRackUnits(request.getRackUnits());
        entity.setWidthMm(request.getWidthMm());
        entity.setDepthMm(request.getDepthMm());
        entity.setMaxWeightKg(request.getMaxWeightKg());
        entity.setRowLabel(request.getRowLabel());
        entity.setPosition(request.getPosition());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(RackUpdateRequest request, Rack entity) {
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
        if (request.getRoomId() != null) {
            entity.setRoomId(request.getRoomId());
        }
        if (request.getRackUnits() != null) {
            entity.setRackUnits(request.getRackUnits());
        }
        if (request.getWidthMm() != null) {
            entity.setWidthMm(request.getWidthMm());
        }
        if (request.getDepthMm() != null) {
            entity.setDepthMm(request.getDepthMm());
        }
        if (request.getMaxWeightKg() != null) {
            entity.setMaxWeightKg(request.getMaxWeightKg());
        }
        if (request.getRowLabel() != null) {
            entity.setRowLabel(request.getRowLabel());
        }
        if (request.getPosition() != null) {
            entity.setPosition(request.getPosition());
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

    public RackResponse toResponse(Rack entity) {
        RackResponse response = new RackResponse();
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
        response.setRoomId(entity.getRoomId());
        response.setRackUnits(entity.getRackUnits());
        response.setWidthMm(entity.getWidthMm());
        response.setDepthMm(entity.getDepthMm());
        response.setMaxWeightKg(entity.getMaxWeightKg());
        response.setRowLabel(entity.getRowLabel());
        response.setPosition(entity.getPosition());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public RackSummary toSummary(Rack entity) {
        return new RackSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RackResponse> toResponses(List<Rack> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Rack entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRoomId(),
                entity.getRackUnits(),
                entity.getWidthMm(),
                entity.getDepthMm(),
                entity.getMaxWeightKg(),
                entity.getRowLabel(),
                entity.getPosition(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
