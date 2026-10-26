package com.uniti.cil.itta.inventory.topology.mapper;

import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.TopologyNode;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between TopologyNode entities and DTOs (kept explicit for auditability). */
@Component
public class TopologyNodeMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "view_id", "entity_ref", "node_type", "x_pos", "y_pos", "layer", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public TopologyNode toEntity(TopologyNodeCreateRequest request) {
        TopologyNode entity = new TopologyNode();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setViewId(request.getViewId());
        entity.setEntityRef(request.getEntityRef());
        entity.setNodeType(request.getNodeType());
        entity.setXPos(request.getXPos());
        entity.setYPos(request.getYPos());
        entity.setLayer(request.getLayer());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(TopologyNodeUpdateRequest request, TopologyNode entity) {
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
        if (request.getViewId() != null) {
            entity.setViewId(request.getViewId());
        }
        if (request.getEntityRef() != null) {
            entity.setEntityRef(request.getEntityRef());
        }
        if (request.getNodeType() != null) {
            entity.setNodeType(request.getNodeType());
        }
        if (request.getXPos() != null) {
            entity.setXPos(request.getXPos());
        }
        if (request.getYPos() != null) {
            entity.setYPos(request.getYPos());
        }
        if (request.getLayer() != null) {
            entity.setLayer(request.getLayer());
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
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
    }

    public TopologyNodeResponse toResponse(TopologyNode entity) {
        TopologyNodeResponse response = new TopologyNodeResponse();
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
        response.setViewId(entity.getViewId());
        response.setEntityRef(entity.getEntityRef());
        response.setNodeType(entity.getNodeType());
        response.setXPos(entity.getXPos());
        response.setYPos(entity.getYPos());
        response.setLayer(entity.getLayer());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public TopologyNodeSummary toSummary(TopologyNode entity) {
        return new TopologyNodeSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TopologyNodeResponse> toResponses(List<TopologyNode> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(TopologyNode entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getViewId(),
                entity.getEntityRef(),
                entity.getNodeType(),
                entity.getXPos(),
                entity.getYPos(),
                entity.getLayer(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
