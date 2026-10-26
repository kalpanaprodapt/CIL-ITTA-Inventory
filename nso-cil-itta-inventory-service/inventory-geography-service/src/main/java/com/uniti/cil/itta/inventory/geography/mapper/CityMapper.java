package com.uniti.cil.itta.inventory.geography.mapper;

import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.City;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between City entities and DTOs (kept explicit for auditability). */
@Component
public class CityMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "region_id", "postal_prefix", "population", "latitude", "longitude", "metro_area", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public City toEntity(CityCreateRequest request) {
        City entity = new City();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setRegionId(request.getRegionId());
        entity.setPostalPrefix(request.getPostalPrefix());
        entity.setPopulation(request.getPopulation());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        entity.setMetroArea(request.getMetroArea());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(CityUpdateRequest request, City entity) {
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
        if (request.getRegionId() != null) {
            entity.setRegionId(request.getRegionId());
        }
        if (request.getPostalPrefix() != null) {
            entity.setPostalPrefix(request.getPostalPrefix());
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
        if (request.getMetroArea() != null) {
            entity.setMetroArea(request.getMetroArea());
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

    public CityResponse toResponse(City entity) {
        CityResponse response = new CityResponse();
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
        response.setRegionId(entity.getRegionId());
        response.setPostalPrefix(entity.getPostalPrefix());
        response.setPopulation(entity.getPopulation());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        response.setMetroArea(entity.getMetroArea());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public CitySummary toSummary(City entity) {
        return new CitySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CityResponse> toResponses(List<City> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(City entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getRegionId(),
                entity.getPostalPrefix(),
                entity.getPopulation(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getMetroArea(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
