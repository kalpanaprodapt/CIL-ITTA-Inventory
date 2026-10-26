package com.uniti.cil.itta.inventory.radio.mapper;

import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.Antenna;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Antenna entities and DTOs (kept explicit for auditability). */
@Component
public class AntennaMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "sector_id", "model", "height_m", "azimuth_deg", "tilt_deg", "gain_dbi", "source_system", "region", "owner", "criticality", "cost_center");

    public Antenna toEntity(AntennaCreateRequest request) {
        Antenna entity = new Antenna();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSectorId(request.getSectorId());
        entity.setModel(request.getModel());
        entity.setHeightM(request.getHeightM());
        entity.setAzimuthDeg(request.getAzimuthDeg());
        entity.setTiltDeg(request.getTiltDeg());
        entity.setGainDbi(request.getGainDbi());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(AntennaUpdateRequest request, Antenna entity) {
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
        if (request.getSectorId() != null) {
            entity.setSectorId(request.getSectorId());
        }
        if (request.getModel() != null) {
            entity.setModel(request.getModel());
        }
        if (request.getHeightM() != null) {
            entity.setHeightM(request.getHeightM());
        }
        if (request.getAzimuthDeg() != null) {
            entity.setAzimuthDeg(request.getAzimuthDeg());
        }
        if (request.getTiltDeg() != null) {
            entity.setTiltDeg(request.getTiltDeg());
        }
        if (request.getGainDbi() != null) {
            entity.setGainDbi(request.getGainDbi());
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
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public AntennaResponse toResponse(Antenna entity) {
        AntennaResponse response = new AntennaResponse();
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
        response.setSectorId(entity.getSectorId());
        response.setModel(entity.getModel());
        response.setHeightM(entity.getHeightM());
        response.setAzimuthDeg(entity.getAzimuthDeg());
        response.setTiltDeg(entity.getTiltDeg());
        response.setGainDbi(entity.getGainDbi());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public AntennaSummary toSummary(Antenna entity) {
        return new AntennaSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AntennaResponse> toResponses(List<Antenna> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Antenna entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSectorId(),
                entity.getModel(),
                entity.getHeightM(),
                entity.getAzimuthDeg(),
                entity.getTiltDeg(),
                entity.getGainDbi(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
