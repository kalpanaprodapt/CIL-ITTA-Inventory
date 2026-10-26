package com.uniti.cil.itta.inventory.change.mapper;

import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.FieldTask;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between FieldTask entities and DTOs (kept explicit for auditability). */
@Component
public class FieldTaskMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "work_order_id", "task_name", "sequence", "duration_minutes", "completed_on", "region", "owner", "criticality", "cost_center", "notes");

    public FieldTask toEntity(FieldTaskCreateRequest request) {
        FieldTask entity = new FieldTask();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setWorkOrderId(request.getWorkOrderId());
        entity.setTaskName(request.getTaskName());
        entity.setSequence(request.getSequence());
        entity.setDurationMinutes(request.getDurationMinutes());
        entity.setCompletedOn(request.getCompletedOn());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(FieldTaskUpdateRequest request, FieldTask entity) {
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
        if (request.getWorkOrderId() != null) {
            entity.setWorkOrderId(request.getWorkOrderId());
        }
        if (request.getTaskName() != null) {
            entity.setTaskName(request.getTaskName());
        }
        if (request.getSequence() != null) {
            entity.setSequence(request.getSequence());
        }
        if (request.getDurationMinutes() != null) {
            entity.setDurationMinutes(request.getDurationMinutes());
        }
        if (request.getCompletedOn() != null) {
            entity.setCompletedOn(request.getCompletedOn());
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

    public FieldTaskResponse toResponse(FieldTask entity) {
        FieldTaskResponse response = new FieldTaskResponse();
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
        response.setWorkOrderId(entity.getWorkOrderId());
        response.setTaskName(entity.getTaskName());
        response.setSequence(entity.getSequence());
        response.setDurationMinutes(entity.getDurationMinutes());
        response.setCompletedOn(entity.getCompletedOn());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public FieldTaskSummary toSummary(FieldTask entity) {
        return new FieldTaskSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<FieldTaskResponse> toResponses(List<FieldTask> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(FieldTask entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getWorkOrderId(),
                entity.getTaskName(),
                entity.getSequence(),
                entity.getDurationMinutes(),
                entity.getCompletedOn(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
