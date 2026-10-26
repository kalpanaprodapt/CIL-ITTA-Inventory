package com.uniti.cil.itta.inventory.geography.mapper;

import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.Region;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Region entities and DTOs (kept explicit for auditability). */
@Component
public class RegionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "country_id", "region_type", "population", "latitude", "longitude", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public Region toEntity(RegionCreateRequest request) {
        Region entity = new Region();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCountryId(request.getCountryId());
        entity.setRegionType(request.getRegionType());
        entity.setPopulation(request.getPopulation());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(RegionUpdateRequest request, Region entity) {
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
        if (request.getCountryId() != null) {
            entity.setCountryId(request.getCountryId());
        }
        if (request.getRegionType() != null) {
            entity.setRegionType(request.getRegionType());
        }
        if (request.getPopulation() != null) {
            entity.setPopulation(request.getPopulation());
        }
        if (request.getLatitude() != null) {
            entity.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            entity.setLongitude(request.getLongitude());
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

    public RegionResponse toResponse(Region entity) {
        RegionResponse response = new RegionResponse();
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
        response.setCountryId(entity.getCountryId());
        response.setRegionType(entity.getRegionType());
        response.setPopulation(entity.getPopulation());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public RegionSummary toSummary(Region entity) {
        return new RegionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RegionResponse> toResponses(List<Region> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Region entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCountryId(),
                entity.getRegionType(),
                entity.getPopulation(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
