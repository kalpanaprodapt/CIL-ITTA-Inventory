package com.uniti.cil.itta.inventory.facility.mapper;

import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.Room;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Room entities and DTOs (kept explicit for auditability). */
@Component
public class RoomMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "floor_id", "room_type", "area_sqm", "cooling_tons", "fire_zone", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public Room toEntity(RoomCreateRequest request) {
        Room entity = new Room();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setFloorId(request.getFloorId());
        entity.setRoomType(request.getRoomType());
        entity.setAreaSqm(request.getAreaSqm());
        entity.setCoolingTons(request.getCoolingTons());
        entity.setFireZone(request.getFireZone());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(RoomUpdateRequest request, Room entity) {
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
        if (request.getFloorId() != null) {
            entity.setFloorId(request.getFloorId());
        }
        if (request.getRoomType() != null) {
            entity.setRoomType(request.getRoomType());
        }
        if (request.getAreaSqm() != null) {
            entity.setAreaSqm(request.getAreaSqm());
        }
        if (request.getCoolingTons() != null) {
            entity.setCoolingTons(request.getCoolingTons());
        }
        if (request.getFireZone() != null) {
            entity.setFireZone(request.getFireZone());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public RoomResponse toResponse(Room entity) {
        RoomResponse response = new RoomResponse();
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
        response.setFloorId(entity.getFloorId());
        response.setRoomType(entity.getRoomType());
        response.setAreaSqm(entity.getAreaSqm());
        response.setCoolingTons(entity.getCoolingTons());
        response.setFireZone(entity.getFireZone());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public RoomSummary toSummary(Room entity) {
        return new RoomSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RoomResponse> toResponses(List<Room> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Room entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getFloorId(),
                entity.getRoomType(),
                entity.getAreaSqm(),
                entity.getCoolingTons(),
                entity.getFireZone(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
