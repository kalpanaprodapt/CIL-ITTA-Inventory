package com.uniti.cil.itta.inventory.geography.mapper;

import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.Building;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Building entities and DTOs (kept explicit for auditability). */
@Component
public class BuildingMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "site_id", "floors", "year_built", "ownership", "lease_expiry", "security_level", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public Building toEntity(BuildingCreateRequest request) {
        Building entity = new Building();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSiteId(request.getSiteId());
        entity.setFloors(request.getFloors());
        entity.setYearBuilt(request.getYearBuilt());
        entity.setOwnership(request.getOwnership());
        entity.setLeaseExpiry(request.getLeaseExpiry());
        entity.setSecurityLevel(request.getSecurityLevel());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(BuildingUpdateRequest request, Building entity) {
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
        if (request.getSiteId() != null) {
            entity.setSiteId(request.getSiteId());
        }
        if (request.getFloors() != null) {
            entity.setFloors(request.getFloors());
        }
        if (request.getYearBuilt() != null) {
            entity.setYearBuilt(request.getYearBuilt());
        }
        if (request.getOwnership() != null) {
            entity.setOwnership(request.getOwnership());
        }
        if (request.getLeaseExpiry() != null) {
            entity.setLeaseExpiry(request.getLeaseExpiry());
        }
        if (request.getSecurityLevel() != null) {
            entity.setSecurityLevel(request.getSecurityLevel());
        }
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
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
    }

    public BuildingResponse toResponse(Building entity) {
        BuildingResponse response = new BuildingResponse();
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
        response.setSiteId(entity.getSiteId());
        response.setFloors(entity.getFloors());
        response.setYearBuilt(entity.getYearBuilt());
        response.setOwnership(entity.getOwnership());
        response.setLeaseExpiry(entity.getLeaseExpiry());
        response.setSecurityLevel(entity.getSecurityLevel());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public BuildingSummary toSummary(Building entity) {
        return new BuildingSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<BuildingResponse> toResponses(List<Building> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Building entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSiteId(),
                entity.getFloors(),
                entity.getYearBuilt(),
                entity.getOwnership(),
                entity.getLeaseExpiry(),
                entity.getSecurityLevel(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
