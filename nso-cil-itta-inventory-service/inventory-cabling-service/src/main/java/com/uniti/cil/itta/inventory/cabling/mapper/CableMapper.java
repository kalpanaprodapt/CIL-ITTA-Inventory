package com.uniti.cil.itta.inventory.cabling.mapper;

import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.Cable;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Cable entities and DTOs (kept explicit for auditability). */
@Component
public class CableMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "cable_type", "length_m", "a_end_port_id", "z_end_port_id", "color", "label", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public Cable toEntity(CableCreateRequest request) {
        Cable entity = new Cable();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCableType(request.getCableType());
        entity.setLengthM(request.getLengthM());
        entity.setAEndPortId(request.getAEndPortId());
        entity.setZEndPortId(request.getZEndPortId());
        entity.setColor(request.getColor());
        entity.setLabel(request.getLabel());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(CableUpdateRequest request, Cable entity) {
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
        if (request.getCableType() != null) {
            entity.setCableType(request.getCableType());
        }
        if (request.getLengthM() != null) {
            entity.setLengthM(request.getLengthM());
        }
        if (request.getAEndPortId() != null) {
            entity.setAEndPortId(request.getAEndPortId());
        }
        if (request.getZEndPortId() != null) {
            entity.setZEndPortId(request.getZEndPortId());
        }
        if (request.getColor() != null) {
            entity.setColor(request.getColor());
        }
        if (request.getLabel() != null) {
            entity.setLabel(request.getLabel());
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

    public CableResponse toResponse(Cable entity) {
        CableResponse response = new CableResponse();
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
        response.setCableType(entity.getCableType());
        response.setLengthM(entity.getLengthM());
        response.setAEndPortId(entity.getAEndPortId());
        response.setZEndPortId(entity.getZEndPortId());
        response.setColor(entity.getColor());
        response.setLabel(entity.getLabel());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public CableSummary toSummary(Cable entity) {
        return new CableSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CableResponse> toResponses(List<Cable> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Cable entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCableType(),
                entity.getLengthM(),
                entity.getAEndPortId(),
                entity.getZEndPortId(),
                entity.getColor(),
                entity.getLabel(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
