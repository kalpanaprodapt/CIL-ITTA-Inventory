package com.uniti.cil.itta.inventory.capacity.mapper;

import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.ThresholdPolicy;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ThresholdPolicy entities and DTOs (kept explicit for auditability). */
@Component
public class ThresholdPolicyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "policy_name", "metric", "warn_pct", "crit_pct", "evaluation_window_minutes", "region", "owner", "criticality", "cost_center", "notes");

    public ThresholdPolicy toEntity(ThresholdPolicyCreateRequest request) {
        ThresholdPolicy entity = new ThresholdPolicy();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPolicyName(request.getPolicyName());
        entity.setMetric(request.getMetric());
        entity.setWarnPct(request.getWarnPct());
        entity.setCritPct(request.getCritPct());
        entity.setEvaluationWindowMinutes(request.getEvaluationWindowMinutes());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(ThresholdPolicyUpdateRequest request, ThresholdPolicy entity) {
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
        if (request.getPolicyName() != null) {
            entity.setPolicyName(request.getPolicyName());
        }
        if (request.getMetric() != null) {
            entity.setMetric(request.getMetric());
        }
        if (request.getWarnPct() != null) {
            entity.setWarnPct(request.getWarnPct());
        }
        if (request.getCritPct() != null) {
            entity.setCritPct(request.getCritPct());
        }
        if (request.getEvaluationWindowMinutes() != null) {
            entity.setEvaluationWindowMinutes(request.getEvaluationWindowMinutes());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
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
    }

    public ThresholdPolicyResponse toResponse(ThresholdPolicy entity) {
        ThresholdPolicyResponse response = new ThresholdPolicyResponse();
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
        response.setPolicyName(entity.getPolicyName());
        response.setMetric(entity.getMetric());
        response.setWarnPct(entity.getWarnPct());
        response.setCritPct(entity.getCritPct());
        response.setEvaluationWindowMinutes(entity.getEvaluationWindowMinutes());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public ThresholdPolicySummary toSummary(ThresholdPolicy entity) {
        return new ThresholdPolicySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ThresholdPolicyResponse> toResponses(List<ThresholdPolicy> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ThresholdPolicy entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPolicyName(),
                entity.getMetric(),
                entity.getWarnPct(),
                entity.getCritPct(),
                entity.getEvaluationWindowMinutes(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
