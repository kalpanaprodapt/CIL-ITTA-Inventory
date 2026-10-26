package com.uniti.cil.itta.inventory.topology.mapper;

import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.TopologyView;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between TopologyView entities and DTOs (kept explicit for auditability). */
@Component
public class TopologyViewMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "view_name", "view_type", "scope_filter", "refreshed_at", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public TopologyView toEntity(TopologyViewCreateRequest request) {
        TopologyView entity = new TopologyView();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setViewName(request.getViewName());
        entity.setViewType(request.getViewType());
        entity.setScopeFilter(request.getScopeFilter());
        entity.setRefreshedAt(request.getRefreshedAt());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(TopologyViewUpdateRequest request, TopologyView entity) {
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
        if (request.getViewName() != null) {
            entity.setViewName(request.getViewName());
        }
        if (request.getViewType() != null) {
            entity.setViewType(request.getViewType());
        }
        if (request.getScopeFilter() != null) {
            entity.setScopeFilter(request.getScopeFilter());
        }
        if (request.getRefreshedAt() != null) {
            entity.setRefreshedAt(request.getRefreshedAt());
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

    public TopologyViewResponse toResponse(TopologyView entity) {
        TopologyViewResponse response = new TopologyViewResponse();
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
        response.setViewName(entity.getViewName());
        response.setViewType(entity.getViewType());
        response.setScopeFilter(entity.getScopeFilter());
        response.setRefreshedAt(entity.getRefreshedAt());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public TopologyViewSummary toSummary(TopologyView entity) {
        return new TopologyViewSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TopologyViewResponse> toResponses(List<TopologyView> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(TopologyView entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getViewName(),
                entity.getViewType(),
                entity.getScopeFilter(),
                entity.getRefreshedAt(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
