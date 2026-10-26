package com.uniti.cil.itta.inventory.vendor.mapper;

import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.HardwareModel;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between HardwareModel entities and DTOs (kept explicit for auditability). */
@Component
public class HardwareModelMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vendor_id", "model_number", "form_factor", "power_watts", "eol_date", "eos_date", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public HardwareModel toEntity(HardwareModelCreateRequest request) {
        HardwareModel entity = new HardwareModel();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVendorId(request.getVendorId());
        entity.setModelNumber(request.getModelNumber());
        entity.setFormFactor(request.getFormFactor());
        entity.setPowerWatts(request.getPowerWatts());
        entity.setEolDate(request.getEolDate());
        entity.setEosDate(request.getEosDate());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(HardwareModelUpdateRequest request, HardwareModel entity) {
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
        if (request.getVendorId() != null) {
            entity.setVendorId(request.getVendorId());
        }
        if (request.getModelNumber() != null) {
            entity.setModelNumber(request.getModelNumber());
        }
        if (request.getFormFactor() != null) {
            entity.setFormFactor(request.getFormFactor());
        }
        if (request.getPowerWatts() != null) {
            entity.setPowerWatts(request.getPowerWatts());
        }
        if (request.getEolDate() != null) {
            entity.setEolDate(request.getEolDate());
        }
        if (request.getEosDate() != null) {
            entity.setEosDate(request.getEosDate());
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

    public HardwareModelResponse toResponse(HardwareModel entity) {
        HardwareModelResponse response = new HardwareModelResponse();
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
        response.setVendorId(entity.getVendorId());
        response.setModelNumber(entity.getModelNumber());
        response.setFormFactor(entity.getFormFactor());
        response.setPowerWatts(entity.getPowerWatts());
        response.setEolDate(entity.getEolDate());
        response.setEosDate(entity.getEosDate());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public HardwareModelSummary toSummary(HardwareModel entity) {
        return new HardwareModelSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<HardwareModelResponse> toResponses(List<HardwareModel> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(HardwareModel entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVendorId(),
                entity.getModelNumber(),
                entity.getFormFactor(),
                entity.getPowerWatts(),
                entity.getEolDate(),
                entity.getEosDate(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
