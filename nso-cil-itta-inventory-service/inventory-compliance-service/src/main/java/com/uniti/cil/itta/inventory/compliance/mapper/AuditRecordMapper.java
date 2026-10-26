package com.uniti.cil.itta.inventory.compliance.mapper;

import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.AuditRecord;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between AuditRecord entities and DTOs (kept explicit for auditability). */
@Component
public class AuditRecordMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "actor", "action", "entity_ref", "occurred_at", "before_state", "after_state", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public AuditRecord toEntity(AuditRecordCreateRequest request) {
        AuditRecord entity = new AuditRecord();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setActor(request.getActor());
        entity.setAction(request.getAction());
        entity.setEntityRef(request.getEntityRef());
        entity.setOccurredAt(request.getOccurredAt());
        entity.setBeforeState(request.getBeforeState());
        entity.setAfterState(request.getAfterState());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(AuditRecordUpdateRequest request, AuditRecord entity) {
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
        if (request.getActor() != null) {
            entity.setActor(request.getActor());
        }
        if (request.getAction() != null) {
            entity.setAction(request.getAction());
        }
        if (request.getEntityRef() != null) {
            entity.setEntityRef(request.getEntityRef());
        }
        if (request.getOccurredAt() != null) {
            entity.setOccurredAt(request.getOccurredAt());
        }
        if (request.getBeforeState() != null) {
            entity.setBeforeState(request.getBeforeState());
        }
        if (request.getAfterState() != null) {
            entity.setAfterState(request.getAfterState());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
    }

    public AuditRecordResponse toResponse(AuditRecord entity) {
        AuditRecordResponse response = new AuditRecordResponse();
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
        response.setActor(entity.getActor());
        response.setAction(entity.getAction());
        response.setEntityRef(entity.getEntityRef());
        response.setOccurredAt(entity.getOccurredAt());
        response.setBeforeState(entity.getBeforeState());
        response.setAfterState(entity.getAfterState());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public AuditRecordSummary toSummary(AuditRecord entity) {
        return new AuditRecordSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AuditRecordResponse> toResponses(List<AuditRecord> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(AuditRecord entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getActor(),
                entity.getAction(),
                entity.getEntityRef(),
                entity.getOccurredAt(),
                entity.getBeforeState(),
                entity.getAfterState(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
