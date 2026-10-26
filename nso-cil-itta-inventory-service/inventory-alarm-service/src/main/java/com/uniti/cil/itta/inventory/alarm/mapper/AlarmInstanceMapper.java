package com.uniti.cil.itta.inventory.alarm.mapper;

import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.AlarmInstance;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between AlarmInstance entities and DTOs (kept explicit for auditability). */
@Component
public class AlarmInstanceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "definition_id", "source_ref", "raised_at", "cleared_at", "acked_by", "alarm_state", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public AlarmInstance toEntity(AlarmInstanceCreateRequest request) {
        AlarmInstance entity = new AlarmInstance();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setDefinitionId(request.getDefinitionId());
        entity.setSourceRef(request.getSourceRef());
        entity.setRaisedAt(request.getRaisedAt());
        entity.setClearedAt(request.getClearedAt());
        entity.setAckedBy(request.getAckedBy());
        entity.setAlarmState(request.getAlarmState());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(AlarmInstanceUpdateRequest request, AlarmInstance entity) {
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
        if (request.getDefinitionId() != null) {
            entity.setDefinitionId(request.getDefinitionId());
        }
        if (request.getSourceRef() != null) {
            entity.setSourceRef(request.getSourceRef());
        }
        if (request.getRaisedAt() != null) {
            entity.setRaisedAt(request.getRaisedAt());
        }
        if (request.getClearedAt() != null) {
            entity.setClearedAt(request.getClearedAt());
        }
        if (request.getAckedBy() != null) {
            entity.setAckedBy(request.getAckedBy());
        }
        if (request.getAlarmState() != null) {
            entity.setAlarmState(request.getAlarmState());
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
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public AlarmInstanceResponse toResponse(AlarmInstance entity) {
        AlarmInstanceResponse response = new AlarmInstanceResponse();
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
        response.setDefinitionId(entity.getDefinitionId());
        response.setSourceRef(entity.getSourceRef());
        response.setRaisedAt(entity.getRaisedAt());
        response.setClearedAt(entity.getClearedAt());
        response.setAckedBy(entity.getAckedBy());
        response.setAlarmState(entity.getAlarmState());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public AlarmInstanceSummary toSummary(AlarmInstance entity) {
        return new AlarmInstanceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AlarmInstanceResponse> toResponses(List<AlarmInstance> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(AlarmInstance entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getDefinitionId(),
                entity.getSourceRef(),
                entity.getRaisedAt(),
                entity.getClearedAt(),
                entity.getAckedBy(),
                entity.getAlarmState(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
