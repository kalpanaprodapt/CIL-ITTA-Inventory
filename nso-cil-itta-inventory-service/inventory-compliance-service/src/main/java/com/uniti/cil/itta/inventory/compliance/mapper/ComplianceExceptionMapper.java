package com.uniti.cil.itta.inventory.compliance.mapper;

import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.ComplianceException;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ComplianceException entities and DTOs (kept explicit for auditability). */
@Component
public class ComplianceExceptionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "rule_id", "target_ref", "justification", "approved_by", "expires_on", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public ComplianceException toEntity(ComplianceExceptionCreateRequest request) {
        ComplianceException entity = new ComplianceException();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRuleId(request.getRuleId());
        entity.setTargetRef(request.getTargetRef());
        entity.setJustification(request.getJustification());
        entity.setApprovedBy(request.getApprovedBy());
        entity.setExpiresOn(request.getExpiresOn());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(ComplianceExceptionUpdateRequest request, ComplianceException entity) {
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
        if (request.getJustification() != null) {
            entity.setJustification(request.getJustification());
        }
        if (request.getApprovedBy() != null) {
            entity.setApprovedBy(request.getApprovedBy());
        }
        if (request.getExpiresOn() != null) {
            entity.setExpiresOn(request.getExpiresOn());
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

    public ComplianceExceptionResponse toResponse(ComplianceException entity) {
        ComplianceExceptionResponse response = new ComplianceExceptionResponse();
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
        response.setJustification(entity.getJustification());
        response.setApprovedBy(entity.getApprovedBy());
        response.setExpiresOn(entity.getExpiresOn());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public ComplianceExceptionSummary toSummary(ComplianceException entity) {
        return new ComplianceExceptionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ComplianceExceptionResponse> toResponses(List<ComplianceException> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ComplianceException entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRuleId(),
                entity.getTargetRef(),
                entity.getJustification(),
                entity.getApprovedBy(),
                entity.getExpiresOn(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
