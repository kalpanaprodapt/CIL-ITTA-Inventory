package com.uniti.cil.itta.inventory.change.mapper;

import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.ChangeRequest;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ChangeRequest entities and DTOs (kept explicit for auditability). */
@Component
public class ChangeRequestMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "change_number", "change_type", "risk_level", "planned_start", "planned_end", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public ChangeRequest toEntity(ChangeRequestCreateRequest request) {
        ChangeRequest entity = new ChangeRequest();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setChangeNumber(request.getChangeNumber());
        entity.setChangeType(request.getChangeType());
        entity.setRiskLevel(request.getRiskLevel());
        entity.setPlannedStart(request.getPlannedStart());
        entity.setPlannedEnd(request.getPlannedEnd());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(ChangeRequestUpdateRequest request, ChangeRequest entity) {
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
        if (request.getChangeNumber() != null) {
            entity.setChangeNumber(request.getChangeNumber());
        }
        if (request.getChangeType() != null) {
            entity.setChangeType(request.getChangeType());
        }
        if (request.getRiskLevel() != null) {
            entity.setRiskLevel(request.getRiskLevel());
        }
        if (request.getPlannedStart() != null) {
            entity.setPlannedStart(request.getPlannedStart());
        }
        if (request.getPlannedEnd() != null) {
            entity.setPlannedEnd(request.getPlannedEnd());
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

    public ChangeRequestResponse toResponse(ChangeRequest entity) {
        ChangeRequestResponse response = new ChangeRequestResponse();
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
        response.setChangeNumber(entity.getChangeNumber());
        response.setChangeType(entity.getChangeType());
        response.setRiskLevel(entity.getRiskLevel());
        response.setPlannedStart(entity.getPlannedStart());
        response.setPlannedEnd(entity.getPlannedEnd());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public ChangeRequestSummary toSummary(ChangeRequest entity) {
        return new ChangeRequestSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ChangeRequestResponse> toResponses(List<ChangeRequest> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ChangeRequest entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getChangeNumber(),
                entity.getChangeType(),
                entity.getRiskLevel(),
                entity.getPlannedStart(),
                entity.getPlannedEnd(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
