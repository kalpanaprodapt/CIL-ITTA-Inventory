package com.uniti.cil.itta.inventory.device.mapper;

import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.LineCard;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between LineCard entities and DTOs (kept explicit for auditability). */
@Component
public class LineCardMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "chassis_id", "slot_number", "card_type", "port_count", "serial_number", "firmware", "region", "owner", "criticality", "cost_center", "notes");

    public LineCard toEntity(LineCardCreateRequest request) {
        LineCard entity = new LineCard();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setChassisId(request.getChassisId());
        entity.setSlotNumber(request.getSlotNumber());
        entity.setCardType(request.getCardType());
        entity.setPortCount(request.getPortCount());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setFirmware(request.getFirmware());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(LineCardUpdateRequest request, LineCard entity) {
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
        if (request.getCardType() != null) {
            entity.setCardType(request.getCardType());
        }
        if (request.getPortCount() != null) {
            entity.setPortCount(request.getPortCount());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
        }
        if (request.getFirmware() != null) {
            entity.setFirmware(request.getFirmware());
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

    public LineCardResponse toResponse(LineCard entity) {
        LineCardResponse response = new LineCardResponse();
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
        response.setCardType(entity.getCardType());
        response.setPortCount(entity.getPortCount());
        response.setSerialNumber(entity.getSerialNumber());
        response.setFirmware(entity.getFirmware());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public LineCardSummary toSummary(LineCard entity) {
        return new LineCardSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<LineCardResponse> toResponses(List<LineCard> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(LineCard entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getChassisId(),
                entity.getSlotNumber(),
                entity.getCardType(),
                entity.getPortCount(),
                entity.getSerialNumber(),
                entity.getFirmware(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
