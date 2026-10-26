package com.uniti.cil.itta.inventory.switching.mapper;

import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.RouteTarget;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between RouteTarget entities and DTOs (kept explicit for auditability). */
@Component
public class RouteTargetMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "asn", "assigned_number", "rt_type", "vrf_id", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public RouteTarget toEntity(RouteTargetCreateRequest request) {
        RouteTarget entity = new RouteTarget();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAsn(request.getAsn());
        entity.setAssignedNumber(request.getAssignedNumber());
        entity.setRtType(request.getRtType());
        entity.setVrfId(request.getVrfId());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(RouteTargetUpdateRequest request, RouteTarget entity) {
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
        if (request.getAsn() != null) {
            entity.setAsn(request.getAsn());
        }
        if (request.getAssignedNumber() != null) {
            entity.setAssignedNumber(request.getAssignedNumber());
        }
        if (request.getRtType() != null) {
            entity.setRtType(request.getRtType());
        }
        if (request.getVrfId() != null) {
            entity.setVrfId(request.getVrfId());
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
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
    }

    public RouteTargetResponse toResponse(RouteTarget entity) {
        RouteTargetResponse response = new RouteTargetResponse();
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
        response.setAsn(entity.getAsn());
        response.setAssignedNumber(entity.getAssignedNumber());
        response.setRtType(entity.getRtType());
        response.setVrfId(entity.getVrfId());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public RouteTargetSummary toSummary(RouteTarget entity) {
        return new RouteTargetSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RouteTargetResponse> toResponses(List<RouteTarget> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(RouteTarget entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAsn(),
                entity.getAssignedNumber(),
                entity.getRtType(),
                entity.getVrfId(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
