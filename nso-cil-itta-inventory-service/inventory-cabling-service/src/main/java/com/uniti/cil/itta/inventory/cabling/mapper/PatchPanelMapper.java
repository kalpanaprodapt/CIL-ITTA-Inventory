package com.uniti.cil.itta.inventory.cabling.mapper;

import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.PatchPanel;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PatchPanel entities and DTOs (kept explicit for auditability). */
@Component
public class PatchPanelMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "room_id", "rack_id", "panel_type", "port_count", "label", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public PatchPanel toEntity(PatchPanelCreateRequest request) {
        PatchPanel entity = new PatchPanel();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRoomId(request.getRoomId());
        entity.setRackId(request.getRackId());
        entity.setPanelType(request.getPanelType());
        entity.setPortCount(request.getPortCount());
        entity.setLabel(request.getLabel());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(PatchPanelUpdateRequest request, PatchPanel entity) {
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
        if (request.getRackId() != null) {
            entity.setRackId(request.getRackId());
        }
        if (request.getPanelType() != null) {
            entity.setPanelType(request.getPanelType());
        }
        if (request.getPortCount() != null) {
            entity.setPortCount(request.getPortCount());
        }
        if (request.getLabel() != null) {
            entity.setLabel(request.getLabel());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
    }

    public PatchPanelResponse toResponse(PatchPanel entity) {
        PatchPanelResponse response = new PatchPanelResponse();
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
        response.setRackId(entity.getRackId());
        response.setPanelType(entity.getPanelType());
        response.setPortCount(entity.getPortCount());
        response.setLabel(entity.getLabel());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public PatchPanelSummary toSummary(PatchPanel entity) {
        return new PatchPanelSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PatchPanelResponse> toResponses(List<PatchPanel> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PatchPanel entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRoomId(),
                entity.getRackId(),
                entity.getPanelType(),
                entity.getPortCount(),
                entity.getLabel(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
