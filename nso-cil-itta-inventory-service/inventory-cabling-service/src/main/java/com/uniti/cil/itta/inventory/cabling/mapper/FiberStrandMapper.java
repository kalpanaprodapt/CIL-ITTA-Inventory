package com.uniti.cil.itta.inventory.cabling.mapper;

import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.FiberStrand;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between FiberStrand entities and DTOs (kept explicit for auditability). */
@Component
public class FiberStrandMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "cable_id", "strand_number", "buffer_tube", "tube_color", "attenuation_db", "in_use", "region", "owner", "criticality", "cost_center", "notes");

    public FiberStrand toEntity(FiberStrandCreateRequest request) {
        FiberStrand entity = new FiberStrand();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCableId(request.getCableId());
        entity.setStrandNumber(request.getStrandNumber());
        entity.setBufferTube(request.getBufferTube());
        entity.setTubeColor(request.getTubeColor());
        entity.setAttenuationDb(request.getAttenuationDb());
        entity.setInUse(request.getInUse());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(FiberStrandUpdateRequest request, FiberStrand entity) {
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
        if (request.getCableId() != null) {
            entity.setCableId(request.getCableId());
        }
        if (request.getStrandNumber() != null) {
            entity.setStrandNumber(request.getStrandNumber());
        }
        if (request.getBufferTube() != null) {
            entity.setBufferTube(request.getBufferTube());
        }
        if (request.getTubeColor() != null) {
            entity.setTubeColor(request.getTubeColor());
        }
        if (request.getAttenuationDb() != null) {
            entity.setAttenuationDb(request.getAttenuationDb());
        }
        if (request.getInUse() != null) {
            entity.setInUse(request.getInUse());
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
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
    }

    public FiberStrandResponse toResponse(FiberStrand entity) {
        FiberStrandResponse response = new FiberStrandResponse();
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
        response.setCableId(entity.getCableId());
        response.setStrandNumber(entity.getStrandNumber());
        response.setBufferTube(entity.getBufferTube());
        response.setTubeColor(entity.getTubeColor());
        response.setAttenuationDb(entity.getAttenuationDb());
        response.setInUse(entity.getInUse());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public FiberStrandSummary toSummary(FiberStrand entity) {
        return new FiberStrandSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<FiberStrandResponse> toResponses(List<FiberStrand> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(FiberStrand entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCableId(),
                entity.getStrandNumber(),
                entity.getBufferTube(),
                entity.getTubeColor(),
                entity.getAttenuationDb(),
                entity.getInUse(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
