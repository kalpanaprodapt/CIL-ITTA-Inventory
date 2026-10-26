package com.uniti.cil.itta.inventory.compliance.mapper;

import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.PolicyDocument;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PolicyDocument entities and DTOs (kept explicit for auditability). */
@Component
public class PolicyDocumentMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "title", "doc_version", "effective_date", "owner", "document_url", "lifecycle_stage", "source_system", "region", "criticality", "cost_center");

    public PolicyDocument toEntity(PolicyDocumentCreateRequest request) {
        PolicyDocument entity = new PolicyDocument();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setTitle(request.getTitle());
        entity.setDocVersion(request.getDocVersion());
        entity.setEffectiveDate(request.getEffectiveDate());
        entity.setOwner(request.getOwner());
        entity.setDocumentUrl(request.getDocumentUrl());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(PolicyDocumentUpdateRequest request, PolicyDocument entity) {
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
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getDocVersion() != null) {
            entity.setDocVersion(request.getDocVersion());
        }
        if (request.getEffectiveDate() != null) {
            entity.setEffectiveDate(request.getEffectiveDate());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
        if (request.getDocumentUrl() != null) {
            entity.setDocumentUrl(request.getDocumentUrl());
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
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public PolicyDocumentResponse toResponse(PolicyDocument entity) {
        PolicyDocumentResponse response = new PolicyDocumentResponse();
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
        response.setTitle(entity.getTitle());
        response.setDocVersion(entity.getDocVersion());
        response.setEffectiveDate(entity.getEffectiveDate());
        response.setOwner(entity.getOwner());
        response.setDocumentUrl(entity.getDocumentUrl());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public PolicyDocumentSummary toSummary(PolicyDocument entity) {
        return new PolicyDocumentSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PolicyDocumentResponse> toResponses(List<PolicyDocument> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PolicyDocument entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getTitle(),
                entity.getDocVersion(),
                entity.getEffectiveDate(),
                entity.getOwner(),
                entity.getDocumentUrl(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
