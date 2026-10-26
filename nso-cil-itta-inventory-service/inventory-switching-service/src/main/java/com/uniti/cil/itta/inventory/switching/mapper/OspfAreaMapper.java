package com.uniti.cil.itta.inventory.switching.mapper;

import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.OspfArea;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between OspfArea entities and DTOs (kept explicit for auditability). */
@Component
public class OspfAreaMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "area_id", "area_type", "device_count", "auth_type", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public OspfArea toEntity(OspfAreaCreateRequest request) {
        OspfArea entity = new OspfArea();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAreaId(request.getAreaId());
        entity.setAreaType(request.getAreaType());
        entity.setDeviceCount(request.getDeviceCount());
        entity.setAuthType(request.getAuthType());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(OspfAreaUpdateRequest request, OspfArea entity) {
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
        if (request.getAreaId() != null) {
            entity.setAreaId(request.getAreaId());
        }
        if (request.getAreaType() != null) {
            entity.setAreaType(request.getAreaType());
        }
        if (request.getDeviceCount() != null) {
            entity.setDeviceCount(request.getDeviceCount());
        }
        if (request.getAuthType() != null) {
            entity.setAuthType(request.getAuthType());
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

    public OspfAreaResponse toResponse(OspfArea entity) {
        OspfAreaResponse response = new OspfAreaResponse();
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
        response.setAreaId(entity.getAreaId());
        response.setAreaType(entity.getAreaType());
        response.setDeviceCount(entity.getDeviceCount());
        response.setAuthType(entity.getAuthType());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public OspfAreaSummary toSummary(OspfArea entity) {
        return new OspfAreaSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<OspfAreaResponse> toResponses(List<OspfArea> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(OspfArea entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAreaId(),
                entity.getAreaType(),
                entity.getDeviceCount(),
                entity.getAuthType(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
