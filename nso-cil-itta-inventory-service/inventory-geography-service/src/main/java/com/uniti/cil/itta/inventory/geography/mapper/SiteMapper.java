package com.uniti.cil.itta.inventory.geography.mapper;

import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.Site;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Site entities and DTOs (kept explicit for auditability). */
@Component
public class SiteMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "city_id", "site_type", "address", "latitude", "longitude", "power_kw", "access_hours", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public Site toEntity(SiteCreateRequest request) {
        Site entity = new Site();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCityId(request.getCityId());
        entity.setSiteType(request.getSiteType());
        entity.setAddress(request.getAddress());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        entity.setPowerKw(request.getPowerKw());
        entity.setAccessHours(request.getAccessHours());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(SiteUpdateRequest request, Site entity) {
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
        if (request.getCityId() != null) {
            entity.setCityId(request.getCityId());
        }
        if (request.getSiteType() != null) {
            entity.setSiteType(request.getSiteType());
        }
        if (request.getAddress() != null) {
            entity.setAddress(request.getAddress());
        }
        if (request.getLatitude() != null) {
            entity.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            entity.setLongitude(request.getLongitude());
        }
        if (request.getPowerKw() != null) {
            entity.setPowerKw(request.getPowerKw());
        }
        if (request.getAccessHours() != null) {
            entity.setAccessHours(request.getAccessHours());
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
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
    }

    public SiteResponse toResponse(Site entity) {
        SiteResponse response = new SiteResponse();
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
        response.setCityId(entity.getCityId());
        response.setSiteType(entity.getSiteType());
        response.setAddress(entity.getAddress());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        response.setPowerKw(entity.getPowerKw());
        response.setAccessHours(entity.getAccessHours());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public SiteSummary toSummary(Site entity) {
        return new SiteSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SiteResponse> toResponses(List<Site> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Site entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCityId(),
                entity.getSiteType(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getPowerKw(),
                entity.getAccessHours(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
