package com.uniti.cil.itta.inventory.software.mapper;

import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.LicenseKey;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between LicenseKey entities and DTOs (kept explicit for auditability). */
@Component
public class LicenseKeyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "pool_id", "key_value", "feature", "expires_on", "assigned_device_id", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public LicenseKey toEntity(LicenseKeyCreateRequest request) {
        LicenseKey entity = new LicenseKey();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPoolId(request.getPoolId());
        entity.setKeyValue(request.getKeyValue());
        entity.setFeature(request.getFeature());
        entity.setExpiresOn(request.getExpiresOn());
        entity.setAssignedDeviceId(request.getAssignedDeviceId());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(LicenseKeyUpdateRequest request, LicenseKey entity) {
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
        if (request.getPoolId() != null) {
            entity.setPoolId(request.getPoolId());
        }
        if (request.getKeyValue() != null) {
            entity.setKeyValue(request.getKeyValue());
        }
        if (request.getFeature() != null) {
            entity.setFeature(request.getFeature());
        }
        if (request.getExpiresOn() != null) {
            entity.setExpiresOn(request.getExpiresOn());
        }
        if (request.getAssignedDeviceId() != null) {
            entity.setAssignedDeviceId(request.getAssignedDeviceId());
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

    public LicenseKeyResponse toResponse(LicenseKey entity) {
        LicenseKeyResponse response = new LicenseKeyResponse();
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
        response.setPoolId(entity.getPoolId());
        response.setKeyValue(entity.getKeyValue());
        response.setFeature(entity.getFeature());
        response.setExpiresOn(entity.getExpiresOn());
        response.setAssignedDeviceId(entity.getAssignedDeviceId());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public LicenseKeySummary toSummary(LicenseKey entity) {
        return new LicenseKeySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<LicenseKeyResponse> toResponses(List<LicenseKey> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(LicenseKey entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPoolId(),
                entity.getKeyValue(),
                entity.getFeature(),
                entity.getExpiresOn(),
                entity.getAssignedDeviceId(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
