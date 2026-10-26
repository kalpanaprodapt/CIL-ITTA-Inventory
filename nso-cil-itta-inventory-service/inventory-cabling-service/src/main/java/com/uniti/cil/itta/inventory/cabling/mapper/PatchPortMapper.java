package com.uniti.cil.itta.inventory.cabling.mapper;

import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.PatchPort;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PatchPort entities and DTOs (kept explicit for auditability). */
@Component
public class PatchPortMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "panel_id", "port_number", "connected_cable_id", "polarity", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public PatchPort toEntity(PatchPortCreateRequest request) {
        PatchPort entity = new PatchPort();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPanelId(request.getPanelId());
        entity.setPortNumber(request.getPortNumber());
        entity.setConnectedCableId(request.getConnectedCableId());
        entity.setPolarity(request.getPolarity());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(PatchPortUpdateRequest request, PatchPort entity) {
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
        if (request.getPanelId() != null) {
            entity.setPanelId(request.getPanelId());
        }
        if (request.getPortNumber() != null) {
            entity.setPortNumber(request.getPortNumber());
        }
        if (request.getConnectedCableId() != null) {
            entity.setConnectedCableId(request.getConnectedCableId());
        }
        if (request.getPolarity() != null) {
            entity.setPolarity(request.getPolarity());
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
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
    }

    public PatchPortResponse toResponse(PatchPort entity) {
        PatchPortResponse response = new PatchPortResponse();
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
        response.setPanelId(entity.getPanelId());
        response.setPortNumber(entity.getPortNumber());
        response.setConnectedCableId(entity.getConnectedCableId());
        response.setPolarity(entity.getPolarity());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public PatchPortSummary toSummary(PatchPort entity) {
        return new PatchPortSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PatchPortResponse> toResponses(List<PatchPort> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PatchPort entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPanelId(),
                entity.getPortNumber(),
                entity.getConnectedCableId(),
                entity.getPolarity(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
