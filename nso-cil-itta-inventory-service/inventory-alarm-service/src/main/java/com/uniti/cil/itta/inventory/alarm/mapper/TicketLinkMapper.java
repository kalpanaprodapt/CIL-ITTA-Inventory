package com.uniti.cil.itta.inventory.alarm.mapper;

import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.TicketLink;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between TicketLink entities and DTOs (kept explicit for auditability). */
@Component
public class TicketLinkMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "alarm_id", "ticket_system", "ticket_number", "linked_at", "owner", "criticality", "cost_center", "notes", "effective_from");

    public TicketLink toEntity(TicketLinkCreateRequest request) {
        TicketLink entity = new TicketLink();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAlarmId(request.getAlarmId());
        entity.setTicketSystem(request.getTicketSystem());
        entity.setTicketNumber(request.getTicketNumber());
        entity.setLinkedAt(request.getLinkedAt());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(TicketLinkUpdateRequest request, TicketLink entity) {
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
        if (request.getAlarmId() != null) {
            entity.setAlarmId(request.getAlarmId());
        }
        if (request.getTicketSystem() != null) {
            entity.setTicketSystem(request.getTicketSystem());
        }
        if (request.getTicketNumber() != null) {
            entity.setTicketNumber(request.getTicketNumber());
        }
        if (request.getLinkedAt() != null) {
            entity.setLinkedAt(request.getLinkedAt());
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

    public TicketLinkResponse toResponse(TicketLink entity) {
        TicketLinkResponse response = new TicketLinkResponse();
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
        response.setAlarmId(entity.getAlarmId());
        response.setTicketSystem(entity.getTicketSystem());
        response.setTicketNumber(entity.getTicketNumber());
        response.setLinkedAt(entity.getLinkedAt());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public TicketLinkSummary toSummary(TicketLink entity) {
        return new TicketLinkSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TicketLinkResponse> toResponses(List<TicketLink> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(TicketLink entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAlarmId(),
                entity.getTicketSystem(),
                entity.getTicketNumber(),
                entity.getLinkedAt(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
