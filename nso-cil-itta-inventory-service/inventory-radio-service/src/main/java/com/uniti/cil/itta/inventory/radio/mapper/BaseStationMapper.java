package com.uniti.cil.itta.inventory.radio.mapper;

import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.BaseStation;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between BaseStation entities and DTOs (kept explicit for auditability). */
@Component
public class BaseStationMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "site_id", "enb_id", "technology", "sector_count", "cell_count", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public BaseStation toEntity(BaseStationCreateRequest request) {
        BaseStation entity = new BaseStation();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSiteId(request.getSiteId());
        entity.setEnbId(request.getEnbId());
        entity.setTechnology(request.getTechnology());
        entity.setSectorCount(request.getSectorCount());
        entity.setCellCount(request.getCellCount());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(BaseStationUpdateRequest request, BaseStation entity) {
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
        if (request.getEnbId() != null) {
            entity.setEnbId(request.getEnbId());
        }
        if (request.getTechnology() != null) {
            entity.setTechnology(request.getTechnology());
        }
        if (request.getSectorCount() != null) {
            entity.setSectorCount(request.getSectorCount());
        }
        if (request.getCellCount() != null) {
            entity.setCellCount(request.getCellCount());
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
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
    }

    public BaseStationResponse toResponse(BaseStation entity) {
        BaseStationResponse response = new BaseStationResponse();
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
        response.setEnbId(entity.getEnbId());
        response.setTechnology(entity.getTechnology());
        response.setSectorCount(entity.getSectorCount());
        response.setCellCount(entity.getCellCount());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public BaseStationSummary toSummary(BaseStation entity) {
        return new BaseStationSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<BaseStationResponse> toResponses(List<BaseStation> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(BaseStation entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSiteId(),
                entity.getEnbId(),
                entity.getTechnology(),
                entity.getSectorCount(),
                entity.getCellCount(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
