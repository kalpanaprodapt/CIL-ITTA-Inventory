package com.uniti.cil.itta.inventory.software.mapper;

import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.Patch;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Patch entities and DTOs (kept explicit for auditability). */
@Component
public class PatchMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "version_id", "patch_id", "severity", "released_on", "reboot_required", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public Patch toEntity(PatchCreateRequest request) {
        Patch entity = new Patch();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVersionId(request.getVersionId());
        entity.setPatchId(request.getPatchId());
        entity.setSeverity(request.getSeverity());
        entity.setReleasedOn(request.getReleasedOn());
        entity.setRebootRequired(request.getRebootRequired());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(PatchUpdateRequest request, Patch entity) {
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
        if (request.getVersionId() != null) {
            entity.setVersionId(request.getVersionId());
        }
        if (request.getPatchId() != null) {
            entity.setPatchId(request.getPatchId());
        }
        if (request.getSeverity() != null) {
            entity.setSeverity(request.getSeverity());
        }
        if (request.getReleasedOn() != null) {
            entity.setReleasedOn(request.getReleasedOn());
        }
        if (request.getRebootRequired() != null) {
            entity.setRebootRequired(request.getRebootRequired());
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

    public PatchResponse toResponse(Patch entity) {
        PatchResponse response = new PatchResponse();
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
        response.setVersionId(entity.getVersionId());
        response.setPatchId(entity.getPatchId());
        response.setSeverity(entity.getSeverity());
        response.setReleasedOn(entity.getReleasedOn());
        response.setRebootRequired(entity.getRebootRequired());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public PatchSummary toSummary(Patch entity) {
        return new PatchSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PatchResponse> toResponses(List<Patch> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Patch entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVersionId(),
                entity.getPatchId(),
                entity.getSeverity(),
                entity.getReleasedOn(),
                entity.getRebootRequired(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
