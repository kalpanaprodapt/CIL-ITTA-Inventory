package com.uniti.cil.itta.inventory.facility.mapper;

import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.PowerFeed;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PowerFeed entities and DTOs (kept explicit for auditability). */
@Component
public class PowerFeedMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "room_id", "feed_type", "voltage", "amperage", "phase", "breaker_label", "redundancy_group", "owner", "criticality", "cost_center", "notes", "effective_from");

    public PowerFeed toEntity(PowerFeedCreateRequest request) {
        PowerFeed entity = new PowerFeed();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRoomId(request.getRoomId());
        entity.setFeedType(request.getFeedType());
        entity.setVoltage(request.getVoltage());
        entity.setAmperage(request.getAmperage());
        entity.setPhase(request.getPhase());
        entity.setBreakerLabel(request.getBreakerLabel());
        entity.setRedundancyGroup(request.getRedundancyGroup());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(PowerFeedUpdateRequest request, PowerFeed entity) {
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
        if (request.getRoomId() != null) {
            entity.setRoomId(request.getRoomId());
        }
        if (request.getFeedType() != null) {
            entity.setFeedType(request.getFeedType());
        }
        if (request.getVoltage() != null) {
            entity.setVoltage(request.getVoltage());
        }
        if (request.getAmperage() != null) {
            entity.setAmperage(request.getAmperage());
        }
        if (request.getPhase() != null) {
            entity.setPhase(request.getPhase());
        }
        if (request.getBreakerLabel() != null) {
            entity.setBreakerLabel(request.getBreakerLabel());
        }
        if (request.getRedundancyGroup() != null) {
            entity.setRedundancyGroup(request.getRedundancyGroup());
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
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
    }

    public PowerFeedResponse toResponse(PowerFeed entity) {
        PowerFeedResponse response = new PowerFeedResponse();
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
        response.setRoomId(entity.getRoomId());
        response.setFeedType(entity.getFeedType());
        response.setVoltage(entity.getVoltage());
        response.setAmperage(entity.getAmperage());
        response.setPhase(entity.getPhase());
        response.setBreakerLabel(entity.getBreakerLabel());
        response.setRedundancyGroup(entity.getRedundancyGroup());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public PowerFeedSummary toSummary(PowerFeed entity) {
        return new PowerFeedSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PowerFeedResponse> toResponses(List<PowerFeed> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PowerFeed entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRoomId(),
                entity.getFeedType(),
                entity.getVoltage(),
                entity.getAmperage(),
                entity.getPhase(),
                entity.getBreakerLabel(),
                entity.getRedundancyGroup(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
