package com.uniti.cil.itta.inventory.optical.mapper;

import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.OpticalChannel;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between OpticalChannel entities and DTOs (kept explicit for auditability). */
@Component
public class OpticalChannelMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "wavelength_nm", "channel_number", "grid_type", "bitrate_gbps", "system_id", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public OpticalChannel toEntity(OpticalChannelCreateRequest request) {
        OpticalChannel entity = new OpticalChannel();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setWavelengthNm(request.getWavelengthNm());
        entity.setChannelNumber(request.getChannelNumber());
        entity.setGridType(request.getGridType());
        entity.setBitrateGbps(request.getBitrateGbps());
        entity.setSystemId(request.getSystemId());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(OpticalChannelUpdateRequest request, OpticalChannel entity) {
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
        if (request.getWavelengthNm() != null) {
            entity.setWavelengthNm(request.getWavelengthNm());
        }
        if (request.getChannelNumber() != null) {
            entity.setChannelNumber(request.getChannelNumber());
        }
        if (request.getGridType() != null) {
            entity.setGridType(request.getGridType());
        }
        if (request.getBitrateGbps() != null) {
            entity.setBitrateGbps(request.getBitrateGbps());
        }
        if (request.getSystemId() != null) {
            entity.setSystemId(request.getSystemId());
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

    public OpticalChannelResponse toResponse(OpticalChannel entity) {
        OpticalChannelResponse response = new OpticalChannelResponse();
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
        response.setWavelengthNm(entity.getWavelengthNm());
        response.setChannelNumber(entity.getChannelNumber());
        response.setGridType(entity.getGridType());
        response.setBitrateGbps(entity.getBitrateGbps());
        response.setSystemId(entity.getSystemId());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public OpticalChannelSummary toSummary(OpticalChannel entity) {
        return new OpticalChannelSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<OpticalChannelResponse> toResponses(List<OpticalChannel> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(OpticalChannel entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getWavelengthNm(),
                entity.getChannelNumber(),
                entity.getGridType(),
                entity.getBitrateGbps(),
                entity.getSystemId(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
