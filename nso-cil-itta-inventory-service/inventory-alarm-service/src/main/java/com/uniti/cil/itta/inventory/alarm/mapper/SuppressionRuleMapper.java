package com.uniti.cil.itta.inventory.alarm.mapper;

import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.SuppressionRule;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SuppressionRule entities and DTOs (kept explicit for auditability). */
@Component
public class SuppressionRuleMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rule_name", "match_expression", "starts_at", "ends_at", "reason", "source_system", "region", "owner", "criticality", "cost_center");

    public SuppressionRule toEntity(SuppressionRuleCreateRequest request) {
        SuppressionRule entity = new SuppressionRule();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRuleName(request.getRuleName());
        entity.setMatchExpression(request.getMatchExpression());
        entity.setStartsAt(request.getStartsAt());
        entity.setEndsAt(request.getEndsAt());
        entity.setReason(request.getReason());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(SuppressionRuleUpdateRequest request, SuppressionRule entity) {
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
        if (request.getRuleName() != null) {
            entity.setRuleName(request.getRuleName());
        }
        if (request.getMatchExpression() != null) {
            entity.setMatchExpression(request.getMatchExpression());
        }
        if (request.getStartsAt() != null) {
            entity.setStartsAt(request.getStartsAt());
        }
        if (request.getEndsAt() != null) {
            entity.setEndsAt(request.getEndsAt());
        }
        if (request.getReason() != null) {
            entity.setReason(request.getReason());
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

    public SuppressionRuleResponse toResponse(SuppressionRule entity) {
        SuppressionRuleResponse response = new SuppressionRuleResponse();
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
        response.setRuleName(entity.getRuleName());
        response.setMatchExpression(entity.getMatchExpression());
        response.setStartsAt(entity.getStartsAt());
        response.setEndsAt(entity.getEndsAt());
        response.setReason(entity.getReason());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public SuppressionRuleSummary toSummary(SuppressionRule entity) {
        return new SuppressionRuleSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SuppressionRuleResponse> toResponses(List<SuppressionRule> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SuppressionRule entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRuleName(),
                entity.getMatchExpression(),
                entity.getStartsAt(),
                entity.getEndsAt(),
                entity.getReason(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
