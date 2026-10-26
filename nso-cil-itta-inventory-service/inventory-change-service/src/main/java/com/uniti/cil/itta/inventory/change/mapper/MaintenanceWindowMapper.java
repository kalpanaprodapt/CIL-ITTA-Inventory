package com.uniti.cil.itta.inventory.change.mapper;

import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.MaintenanceWindow;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between MaintenanceWindow entities and DTOs (kept explicit for auditability). */
@Component
public class MaintenanceWindowMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "window_name", "starts_at", "ends_at", "scope", "impact_level", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public MaintenanceWindow toEntity(MaintenanceWindowCreateRequest request) {
        MaintenanceWindow entity = new MaintenanceWindow();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setWindowName(request.getWindowName());
        entity.setStartsAt(request.getStartsAt());
        entity.setEndsAt(request.getEndsAt());
        entity.setScope(request.getScope());
        entity.setImpactLevel(request.getImpactLevel());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(MaintenanceWindowUpdateRequest request, MaintenanceWindow entity) {
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
        if (request.getWindowName() != null) {
            entity.setWindowName(request.getWindowName());
        }
        if (request.getStartsAt() != null) {
            entity.setStartsAt(request.getStartsAt());
        }
        if (request.getEndsAt() != null) {
            entity.setEndsAt(request.getEndsAt());
        }
        if (request.getScope() != null) {
            entity.setScope(request.getScope());
        }
        if (request.getImpactLevel() != null) {
            entity.setImpactLevel(request.getImpactLevel());
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

    public MaintenanceWindowResponse toResponse(MaintenanceWindow entity) {
        MaintenanceWindowResponse response = new MaintenanceWindowResponse();
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
        response.setWindowName(entity.getWindowName());
        response.setStartsAt(entity.getStartsAt());
        response.setEndsAt(entity.getEndsAt());
        response.setScope(entity.getScope());
        response.setImpactLevel(entity.getImpactLevel());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public MaintenanceWindowSummary toSummary(MaintenanceWindow entity) {
        return new MaintenanceWindowSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<MaintenanceWindowResponse> toResponses(List<MaintenanceWindow> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(MaintenanceWindow entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getWindowName(),
                entity.getStartsAt(),
                entity.getEndsAt(),
                entity.getScope(),
                entity.getImpactLevel(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
