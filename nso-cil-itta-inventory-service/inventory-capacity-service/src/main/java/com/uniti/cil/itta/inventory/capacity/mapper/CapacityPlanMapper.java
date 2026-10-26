package com.uniti.cil.itta.inventory.capacity.mapper;

import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.CapacityPlan;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CapacityPlan entities and DTOs (kept explicit for auditability). */
@Component
public class CapacityPlanMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "plan_name", "horizon_months", "target_utilization_pct", "owner", "approved_on", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public CapacityPlan toEntity(CapacityPlanCreateRequest request) {
        CapacityPlan entity = new CapacityPlan();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPlanName(request.getPlanName());
        entity.setHorizonMonths(request.getHorizonMonths());
        entity.setTargetUtilizationPct(request.getTargetUtilizationPct());
        entity.setOwner(request.getOwner());
        entity.setApprovedOn(request.getApprovedOn());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(CapacityPlanUpdateRequest request, CapacityPlan entity) {
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
        if (request.getPlanName() != null) {
            entity.setPlanName(request.getPlanName());
        }
        if (request.getHorizonMonths() != null) {
            entity.setHorizonMonths(request.getHorizonMonths());
        }
        if (request.getTargetUtilizationPct() != null) {
            entity.setTargetUtilizationPct(request.getTargetUtilizationPct());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
        if (request.getApprovedOn() != null) {
            entity.setApprovedOn(request.getApprovedOn());
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

    public CapacityPlanResponse toResponse(CapacityPlan entity) {
        CapacityPlanResponse response = new CapacityPlanResponse();
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
        response.setPlanName(entity.getPlanName());
        response.setHorizonMonths(entity.getHorizonMonths());
        response.setTargetUtilizationPct(entity.getTargetUtilizationPct());
        response.setOwner(entity.getOwner());
        response.setApprovedOn(entity.getApprovedOn());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public CapacityPlanSummary toSummary(CapacityPlan entity) {
        return new CapacityPlanSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CapacityPlanResponse> toResponses(List<CapacityPlan> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CapacityPlan entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPlanName(),
                entity.getHorizonMonths(),
                entity.getTargetUtilizationPct(),
                entity.getOwner(),
                entity.getApprovedOn(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
