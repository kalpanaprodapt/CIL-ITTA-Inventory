package com.uniti.cil.itta.inventory.optical.mapper;

import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.Roadm;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Roadm entities and DTOs (kept explicit for auditability). */
@Component
public class RoadmMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "system_id", "degrees", "colorless", "directionless", "contentionless", "region", "owner", "criticality", "cost_center", "notes");

    public Roadm toEntity(RoadmCreateRequest request) {
        Roadm entity = new Roadm();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSystemId(request.getSystemId());
        entity.setDegrees(request.getDegrees());
        entity.setColorless(request.getColorless());
        entity.setDirectionless(request.getDirectionless());
        entity.setContentionless(request.getContentionless());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(RoadmUpdateRequest request, Roadm entity) {
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
        if (request.getSystemId() != null) {
            entity.setSystemId(request.getSystemId());
        }
        if (request.getDegrees() != null) {
            entity.setDegrees(request.getDegrees());
        }
        if (request.getColorless() != null) {
            entity.setColorless(request.getColorless());
        }
        if (request.getDirectionless() != null) {
            entity.setDirectionless(request.getDirectionless());
        }
        if (request.getContentionless() != null) {
            entity.setContentionless(request.getContentionless());
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

    public RoadmResponse toResponse(Roadm entity) {
        RoadmResponse response = new RoadmResponse();
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
        response.setSystemId(entity.getSystemId());
        response.setDegrees(entity.getDegrees());
        response.setColorless(entity.getColorless());
        response.setDirectionless(entity.getDirectionless());
        response.setContentionless(entity.getContentionless());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public RoadmSummary toSummary(Roadm entity) {
        return new RoadmSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RoadmResponse> toResponses(List<Roadm> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Roadm entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSystemId(),
                entity.getDegrees(),
                entity.getColorless(),
                entity.getDirectionless(),
                entity.getContentionless(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
