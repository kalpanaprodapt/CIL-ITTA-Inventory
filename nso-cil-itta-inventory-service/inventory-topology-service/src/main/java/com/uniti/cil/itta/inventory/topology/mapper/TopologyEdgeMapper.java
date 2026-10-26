package com.uniti.cil.itta.inventory.topology.mapper;

import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.TopologyEdge;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between TopologyEdge entities and DTOs (kept explicit for auditability). */
@Component
public class TopologyEdgeMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "view_id", "source_node_id", "target_node_id", "weight", "directed", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public TopologyEdge toEntity(TopologyEdgeCreateRequest request) {
        TopologyEdge entity = new TopologyEdge();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setViewId(request.getViewId());
        entity.setSourceNodeId(request.getSourceNodeId());
        entity.setTargetNodeId(request.getTargetNodeId());
        entity.setWeight(request.getWeight());
        entity.setDirected(request.getDirected());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(TopologyEdgeUpdateRequest request, TopologyEdge entity) {
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
        if (request.getSourceNodeId() != null) {
            entity.setSourceNodeId(request.getSourceNodeId());
        }
        if (request.getTargetNodeId() != null) {
            entity.setTargetNodeId(request.getTargetNodeId());
        }
        if (request.getWeight() != null) {
            entity.setWeight(request.getWeight());
        }
        if (request.getDirected() != null) {
            entity.setDirected(request.getDirected());
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

    public TopologyEdgeResponse toResponse(TopologyEdge entity) {
        TopologyEdgeResponse response = new TopologyEdgeResponse();
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
        response.setSourceNodeId(entity.getSourceNodeId());
        response.setTargetNodeId(entity.getTargetNodeId());
        response.setWeight(entity.getWeight());
        response.setDirected(entity.getDirected());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public TopologyEdgeSummary toSummary(TopologyEdge entity) {
        return new TopologyEdgeSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TopologyEdgeResponse> toResponses(List<TopologyEdge> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(TopologyEdge entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getViewId(),
                entity.getSourceNodeId(),
                entity.getTargetNodeId(),
                entity.getWeight(),
                entity.getDirected(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
