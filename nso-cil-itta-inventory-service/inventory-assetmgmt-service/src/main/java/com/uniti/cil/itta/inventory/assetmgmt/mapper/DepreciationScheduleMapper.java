package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.DepreciationSchedule;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between DepreciationSchedule entities and DTOs (kept explicit for auditability). */
@Component
public class DepreciationScheduleMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "asset_id", "method", "useful_life_months", "salvage_value", "start_date", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public DepreciationSchedule toEntity(DepreciationScheduleCreateRequest request) {
        DepreciationSchedule entity = new DepreciationSchedule();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAssetId(request.getAssetId());
        entity.setMethod(request.getMethod());
        entity.setUsefulLifeMonths(request.getUsefulLifeMonths());
        entity.setSalvageValue(request.getSalvageValue());
        entity.setStartDate(request.getStartDate());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(DepreciationScheduleUpdateRequest request, DepreciationSchedule entity) {
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
        if (request.getAssetId() != null) {
            entity.setAssetId(request.getAssetId());
        }
        if (request.getMethod() != null) {
            entity.setMethod(request.getMethod());
        }
        if (request.getUsefulLifeMonths() != null) {
            entity.setUsefulLifeMonths(request.getUsefulLifeMonths());
        }
        if (request.getSalvageValue() != null) {
            entity.setSalvageValue(request.getSalvageValue());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
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
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public DepreciationScheduleResponse toResponse(DepreciationSchedule entity) {
        DepreciationScheduleResponse response = new DepreciationScheduleResponse();
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
        response.setAssetId(entity.getAssetId());
        response.setMethod(entity.getMethod());
        response.setUsefulLifeMonths(entity.getUsefulLifeMonths());
        response.setSalvageValue(entity.getSalvageValue());
        response.setStartDate(entity.getStartDate());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public DepreciationScheduleSummary toSummary(DepreciationSchedule entity) {
        return new DepreciationScheduleSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<DepreciationScheduleResponse> toResponses(List<DepreciationSchedule> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(DepreciationSchedule entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAssetId(),
                entity.getMethod(),
                entity.getUsefulLifeMonths(),
                entity.getSalvageValue(),
                entity.getStartDate(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
