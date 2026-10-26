package com.uniti.cil.itta.inventory.compliance.mapper;

import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceCheck;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ComplianceCheck entities and DTOs (kept explicit for auditability). */
@Component
public class ComplianceCheckMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rule_id", "target_ref", "checked_at", "result", "evidence", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public ComplianceCheck toEntity(ComplianceCheckCreateRequest request) {
        ComplianceCheck entity = new ComplianceCheck();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRuleId(request.getRuleId());
        entity.setTargetRef(request.getTargetRef());
        entity.setCheckedAt(request.getCheckedAt());
        entity.setResult(request.getResult());
        entity.setEvidence(request.getEvidence());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(ComplianceCheckUpdateRequest request, ComplianceCheck entity) {
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
        if (request.getRuleId() != null) {
            entity.setRuleId(request.getRuleId());
        }
        if (request.getTargetRef() != null) {
            entity.setTargetRef(request.getTargetRef());
        }
        if (request.getCheckedAt() != null) {
            entity.setCheckedAt(request.getCheckedAt());
        }
        if (request.getResult() != null) {
            entity.setResult(request.getResult());
        }
        if (request.getEvidence() != null) {
            entity.setEvidence(request.getEvidence());
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

    public ComplianceCheckResponse toResponse(ComplianceCheck entity) {
        ComplianceCheckResponse response = new ComplianceCheckResponse();
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
        response.setRuleId(entity.getRuleId());
        response.setTargetRef(entity.getTargetRef());
        response.setCheckedAt(entity.getCheckedAt());
        response.setResult(entity.getResult());
        response.setEvidence(entity.getEvidence());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public ComplianceCheckSummary toSummary(ComplianceCheck entity) {
        return new ComplianceCheckSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ComplianceCheckResponse> toResponses(List<ComplianceCheck> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ComplianceCheck entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRuleId(),
                entity.getTargetRef(),
                entity.getCheckedAt(),
                entity.getResult(),
                entity.getEvidence(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
