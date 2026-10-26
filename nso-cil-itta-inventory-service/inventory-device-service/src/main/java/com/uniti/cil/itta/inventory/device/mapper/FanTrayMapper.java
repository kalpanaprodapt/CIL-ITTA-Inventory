package com.uniti.cil.itta.inventory.device.mapper;

import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.FanTray;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between FanTray entities and DTOs (kept explicit for auditability). */
@Component
public class FanTrayMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "chassis_id", "slot_number", "fan_count", "airflow", "serial_number", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public FanTray toEntity(FanTrayCreateRequest request) {
        FanTray entity = new FanTray();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setChassisId(request.getChassisId());
        entity.setSlotNumber(request.getSlotNumber());
        entity.setFanCount(request.getFanCount());
        entity.setAirflow(request.getAirflow());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(FanTrayUpdateRequest request, FanTray entity) {
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
        if (request.getChassisId() != null) {
            entity.setChassisId(request.getChassisId());
        }
        if (request.getSlotNumber() != null) {
            entity.setSlotNumber(request.getSlotNumber());
        }
        if (request.getFanCount() != null) {
            entity.setFanCount(request.getFanCount());
        }
        if (request.getAirflow() != null) {
            entity.setAirflow(request.getAirflow());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
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

    public FanTrayResponse toResponse(FanTray entity) {
        FanTrayResponse response = new FanTrayResponse();
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
        response.setChassisId(entity.getChassisId());
        response.setSlotNumber(entity.getSlotNumber());
        response.setFanCount(entity.getFanCount());
        response.setAirflow(entity.getAirflow());
        response.setSerialNumber(entity.getSerialNumber());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public FanTraySummary toSummary(FanTray entity) {
        return new FanTraySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<FanTrayResponse> toResponses(List<FanTray> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(FanTray entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getChassisId(),
                entity.getSlotNumber(),
                entity.getFanCount(),
                entity.getAirflow(),
                entity.getSerialNumber(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
