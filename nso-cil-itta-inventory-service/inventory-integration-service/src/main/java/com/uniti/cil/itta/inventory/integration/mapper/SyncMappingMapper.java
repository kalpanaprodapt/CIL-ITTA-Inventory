package com.uniti.cil.itta.inventory.integration.mapper;

import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.SyncMapping;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SyncMapping entities and DTOs (kept explicit for auditability). */
@Component
public class SyncMappingMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "job_id", "source_field", "target_field", "transform", "required", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public SyncMapping toEntity(SyncMappingCreateRequest request) {
        SyncMapping entity = new SyncMapping();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setJobId(request.getJobId());
        entity.setSourceField(request.getSourceField());
        entity.setTargetField(request.getTargetField());
        entity.setTransform(request.getTransform());
        entity.setRequired(request.getRequired());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(SyncMappingUpdateRequest request, SyncMapping entity) {
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
        if (request.getSourceField() != null) {
            entity.setSourceField(request.getSourceField());
        }
        if (request.getTargetField() != null) {
            entity.setTargetField(request.getTargetField());
        }
        if (request.getTransform() != null) {
            entity.setTransform(request.getTransform());
        }
        if (request.getRequired() != null) {
            entity.setRequired(request.getRequired());
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
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
    }

    public SyncMappingResponse toResponse(SyncMapping entity) {
        SyncMappingResponse response = new SyncMappingResponse();
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
        response.setSourceField(entity.getSourceField());
        response.setTargetField(entity.getTargetField());
        response.setTransform(entity.getTransform());
        response.setRequired(entity.getRequired());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public SyncMappingSummary toSummary(SyncMapping entity) {
        return new SyncMappingSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SyncMappingResponse> toResponses(List<SyncMapping> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SyncMapping entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getJobId(),
                entity.getSourceField(),
                entity.getTargetField(),
                entity.getTransform(),
                entity.getRequired(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
