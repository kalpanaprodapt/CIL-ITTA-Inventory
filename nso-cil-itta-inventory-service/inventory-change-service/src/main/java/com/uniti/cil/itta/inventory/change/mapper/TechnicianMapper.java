package com.uniti.cil.itta.inventory.change.mapper;

import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.Technician;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Technician entities and DTOs (kept explicit for auditability). */
@Component
public class TechnicianMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "employee_id", "full_name", "skill_set", "home_site_id", "certification_level", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public Technician toEntity(TechnicianCreateRequest request) {
        Technician entity = new Technician();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setEmployeeId(request.getEmployeeId());
        entity.setFullName(request.getFullName());
        entity.setSkillSet(request.getSkillSet());
        entity.setHomeSiteId(request.getHomeSiteId());
        entity.setCertificationLevel(request.getCertificationLevel());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(TechnicianUpdateRequest request, Technician entity) {
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
        if (request.getEmployeeId() != null) {
            entity.setEmployeeId(request.getEmployeeId());
        }
        if (request.getFullName() != null) {
            entity.setFullName(request.getFullName());
        }
        if (request.getSkillSet() != null) {
            entity.setSkillSet(request.getSkillSet());
        }
        if (request.getHomeSiteId() != null) {
            entity.setHomeSiteId(request.getHomeSiteId());
        }
        if (request.getCertificationLevel() != null) {
            entity.setCertificationLevel(request.getCertificationLevel());
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

    public TechnicianResponse toResponse(Technician entity) {
        TechnicianResponse response = new TechnicianResponse();
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
        response.setEmployeeId(entity.getEmployeeId());
        response.setFullName(entity.getFullName());
        response.setSkillSet(entity.getSkillSet());
        response.setHomeSiteId(entity.getHomeSiteId());
        response.setCertificationLevel(entity.getCertificationLevel());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public TechnicianSummary toSummary(Technician entity) {
        return new TechnicianSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TechnicianResponse> toResponses(List<Technician> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Technician entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getEmployeeId(),
                entity.getFullName(),
                entity.getSkillSet(),
                entity.getHomeSiteId(),
                entity.getCertificationLevel(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
