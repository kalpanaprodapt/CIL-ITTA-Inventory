package com.uniti.cil.itta.inventory.facility.mapper;

import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.Floor;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Floor entities and DTOs (kept explicit for auditability). */
@Component
public class FloorMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "building_id", "level", "area_sqm", "load_capacity_kg", "has_raised_floor", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public Floor toEntity(FloorCreateRequest request) {
        Floor entity = new Floor();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setBuildingId(request.getBuildingId());
        entity.setLevel(request.getLevel());
        entity.setAreaSqm(request.getAreaSqm());
        entity.setLoadCapacityKg(request.getLoadCapacityKg());
        entity.setHasRaisedFloor(request.getHasRaisedFloor());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(FloorUpdateRequest request, Floor entity) {
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
        if (request.getBuildingId() != null) {
            entity.setBuildingId(request.getBuildingId());
        }
        if (request.getLevel() != null) {
            entity.setLevel(request.getLevel());
        }
        if (request.getAreaSqm() != null) {
            entity.setAreaSqm(request.getAreaSqm());
        }
        if (request.getLoadCapacityKg() != null) {
            entity.setLoadCapacityKg(request.getLoadCapacityKg());
        }
        if (request.getHasRaisedFloor() != null) {
            entity.setHasRaisedFloor(request.getHasRaisedFloor());
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
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
    }

    public FloorResponse toResponse(Floor entity) {
        FloorResponse response = new FloorResponse();
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
        response.setBuildingId(entity.getBuildingId());
        response.setLevel(entity.getLevel());
        response.setAreaSqm(entity.getAreaSqm());
        response.setLoadCapacityKg(entity.getLoadCapacityKg());
        response.setHasRaisedFloor(entity.getHasRaisedFloor());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public FloorSummary toSummary(Floor entity) {
        return new FloorSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<FloorResponse> toResponses(List<Floor> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Floor entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getBuildingId(),
                entity.getLevel(),
                entity.getAreaSqm(),
                entity.getLoadCapacityKg(),
                entity.getHasRaisedFloor(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
