package com.uniti.cil.itta.inventory.discovery.mapper;

import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationResult;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ReconciliationResult entities and DTOs (kept explicit for auditability). */
@Component
public class ReconciliationResultMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "job_id", "rule_id", "outcome", "diff_summary", "resolved_by", "source_system", "region", "owner", "criticality", "cost_center");

    public ReconciliationResult toEntity(ReconciliationResultCreateRequest request) {
        ReconciliationResult entity = new ReconciliationResult();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setJobId(request.getJobId());
        entity.setRuleId(request.getRuleId());
        entity.setOutcome(request.getOutcome());
        entity.setDiffSummary(request.getDiffSummary());
        entity.setResolvedBy(request.getResolvedBy());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(ReconciliationResultUpdateRequest request, ReconciliationResult entity) {
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
        if (request.getJobId() != null) {
            entity.setJobId(request.getJobId());
        }
        if (request.getRuleId() != null) {
            entity.setRuleId(request.getRuleId());
        }
        if (request.getOutcome() != null) {
            entity.setOutcome(request.getOutcome());
        }
        if (request.getDiffSummary() != null) {
            entity.setDiffSummary(request.getDiffSummary());
        }
        if (request.getResolvedBy() != null) {
            entity.setResolvedBy(request.getResolvedBy());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
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
    }

    public ReconciliationResultResponse toResponse(ReconciliationResult entity) {
        ReconciliationResultResponse response = new ReconciliationResultResponse();
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
        response.setJobId(entity.getJobId());
        response.setRuleId(entity.getRuleId());
        response.setOutcome(entity.getOutcome());
        response.setDiffSummary(entity.getDiffSummary());
        response.setResolvedBy(entity.getResolvedBy());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public ReconciliationResultSummary toSummary(ReconciliationResult entity) {
        return new ReconciliationResultSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ReconciliationResultResponse> toResponses(List<ReconciliationResult> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ReconciliationResult entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getJobId(),
                entity.getRuleId(),
                entity.getOutcome(),
                entity.getDiffSummary(),
                entity.getResolvedBy(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
