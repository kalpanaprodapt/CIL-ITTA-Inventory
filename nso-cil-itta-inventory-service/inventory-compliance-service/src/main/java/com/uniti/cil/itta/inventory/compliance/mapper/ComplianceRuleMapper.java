package com.uniti.cil.itta.inventory.compliance.mapper;

import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceRule;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ComplianceRule entities and DTOs (kept explicit for auditability). */
@Component
public class ComplianceRuleMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rule_code", "framework", "severity", "expression", "region", "owner", "criticality", "cost_center", "notes");

    public ComplianceRule toEntity(ComplianceRuleCreateRequest request) {
        ComplianceRule entity = new ComplianceRule();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRuleCode(request.getRuleCode());
        entity.setFramework(request.getFramework());
        entity.setSeverity(request.getSeverity());
        entity.setExpression(request.getExpression());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(ComplianceRuleUpdateRequest request, ComplianceRule entity) {
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
        if (request.getRuleCode() != null) {
            entity.setRuleCode(request.getRuleCode());
        }
        if (request.getFramework() != null) {
            entity.setFramework(request.getFramework());
        }
        if (request.getSeverity() != null) {
            entity.setSeverity(request.getSeverity());
        }
        if (request.getExpression() != null) {
            entity.setExpression(request.getExpression());
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

    public ComplianceRuleResponse toResponse(ComplianceRule entity) {
        ComplianceRuleResponse response = new ComplianceRuleResponse();
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
        response.setRuleCode(entity.getRuleCode());
        response.setFramework(entity.getFramework());
        response.setSeverity(entity.getSeverity());
        response.setExpression(entity.getExpression());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public ComplianceRuleSummary toSummary(ComplianceRule entity) {
        return new ComplianceRuleSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ComplianceRuleResponse> toResponses(List<ComplianceRule> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ComplianceRule entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRuleCode(),
                entity.getFramework(),
                entity.getSeverity(),
                entity.getExpression(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
