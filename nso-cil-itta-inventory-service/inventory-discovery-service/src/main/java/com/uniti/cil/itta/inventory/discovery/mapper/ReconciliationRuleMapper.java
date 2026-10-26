package com.uniti.cil.itta.inventory.discovery.mapper;

import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.ReconciliationRule;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ReconciliationRule entities and DTOs (kept explicit for auditability). */
@Component
public class ReconciliationRuleMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rule_name", "match_key", "action", "priority", "expression", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public ReconciliationRule toEntity(ReconciliationRuleCreateRequest request) {
        ReconciliationRule entity = new ReconciliationRule();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRuleName(request.getRuleName());
        entity.setMatchKey(request.getMatchKey());
        entity.setAction(request.getAction());
        entity.setPriority(request.getPriority());
        entity.setExpression(request.getExpression());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(ReconciliationRuleUpdateRequest request, ReconciliationRule entity) {
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
        if (request.getMatchKey() != null) {
            entity.setMatchKey(request.getMatchKey());
        }
        if (request.getAction() != null) {
            entity.setAction(request.getAction());
        }
        if (request.getPriority() != null) {
            entity.setPriority(request.getPriority());
        }
        if (request.getExpression() != null) {
            entity.setExpression(request.getExpression());
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
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
    }

    public ReconciliationRuleResponse toResponse(ReconciliationRule entity) {
        ReconciliationRuleResponse response = new ReconciliationRuleResponse();
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
        response.setMatchKey(entity.getMatchKey());
        response.setAction(entity.getAction());
        response.setPriority(entity.getPriority());
        response.setExpression(entity.getExpression());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public ReconciliationRuleSummary toSummary(ReconciliationRule entity) {
        return new ReconciliationRuleSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ReconciliationRuleResponse> toResponses(List<ReconciliationRule> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ReconciliationRule entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRuleName(),
                entity.getMatchKey(),
                entity.getAction(),
                entity.getPriority(),
                entity.getExpression(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
