package com.uniti.cil.itta.inventory.alarm.mapper;

import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.AlarmDefinition;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between AlarmDefinition entities and DTOs (kept explicit for auditability). */
@Component
public class AlarmDefinitionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "alarm_code", "severity", "probable_cause", "auto_clear", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public AlarmDefinition toEntity(AlarmDefinitionCreateRequest request) {
        AlarmDefinition entity = new AlarmDefinition();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAlarmCode(request.getAlarmCode());
        entity.setSeverity(request.getSeverity());
        entity.setProbableCause(request.getProbableCause());
        entity.setAutoClear(request.getAutoClear());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(AlarmDefinitionUpdateRequest request, AlarmDefinition entity) {
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
        if (request.getAlarmCode() != null) {
            entity.setAlarmCode(request.getAlarmCode());
        }
        if (request.getSeverity() != null) {
            entity.setSeverity(request.getSeverity());
        }
        if (request.getProbableCause() != null) {
            entity.setProbableCause(request.getProbableCause());
        }
        if (request.getAutoClear() != null) {
            entity.setAutoClear(request.getAutoClear());
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

    public AlarmDefinitionResponse toResponse(AlarmDefinition entity) {
        AlarmDefinitionResponse response = new AlarmDefinitionResponse();
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
        response.setAlarmCode(entity.getAlarmCode());
        response.setSeverity(entity.getSeverity());
        response.setProbableCause(entity.getProbableCause());
        response.setAutoClear(entity.getAutoClear());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public AlarmDefinitionSummary toSummary(AlarmDefinition entity) {
        return new AlarmDefinitionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AlarmDefinitionResponse> toResponses(List<AlarmDefinition> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(AlarmDefinition entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAlarmCode(),
                entity.getSeverity(),
                entity.getProbableCause(),
                entity.getAutoClear(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
