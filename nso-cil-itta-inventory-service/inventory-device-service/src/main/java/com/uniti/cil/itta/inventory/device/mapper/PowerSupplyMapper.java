package com.uniti.cil.itta.inventory.device.mapper;

import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.PowerSupply;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PowerSupply entities and DTOs (kept explicit for auditability). */
@Component
public class PowerSupplyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "chassis_id", "slot_number", "wattage", "feed_id", "serial_number", "efficiency", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public PowerSupply toEntity(PowerSupplyCreateRequest request) {
        PowerSupply entity = new PowerSupply();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setChassisId(request.getChassisId());
        entity.setSlotNumber(request.getSlotNumber());
        entity.setWattage(request.getWattage());
        entity.setFeedId(request.getFeedId());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setEfficiency(request.getEfficiency());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(PowerSupplyUpdateRequest request, PowerSupply entity) {
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
        if (request.getWattage() != null) {
            entity.setWattage(request.getWattage());
        }
        if (request.getFeedId() != null) {
            entity.setFeedId(request.getFeedId());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
        }
        if (request.getEfficiency() != null) {
            entity.setEfficiency(request.getEfficiency());
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

    public PowerSupplyResponse toResponse(PowerSupply entity) {
        PowerSupplyResponse response = new PowerSupplyResponse();
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
        response.setWattage(entity.getWattage());
        response.setFeedId(entity.getFeedId());
        response.setSerialNumber(entity.getSerialNumber());
        response.setEfficiency(entity.getEfficiency());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public PowerSupplySummary toSummary(PowerSupply entity) {
        return new PowerSupplySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PowerSupplyResponse> toResponses(List<PowerSupply> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PowerSupply entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getChassisId(),
                entity.getSlotNumber(),
                entity.getWattage(),
                entity.getFeedId(),
                entity.getSerialNumber(),
                entity.getEfficiency(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
