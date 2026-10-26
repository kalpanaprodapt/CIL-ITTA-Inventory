package com.uniti.cil.itta.inventory.port.mapper;

import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.Port;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Port entities and DTOs (kept explicit for auditability). */
@Component
public class PortMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "device_id", "card_id", "port_number", "speed_mbps", "media_type", "admin_state", "source_system", "region", "owner", "criticality", "cost_center");

    public Port toEntity(PortCreateRequest request) {
        Port entity = new Port();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setDeviceId(request.getDeviceId());
        entity.setCardId(request.getCardId());
        entity.setPortNumber(request.getPortNumber());
        entity.setSpeedMbps(request.getSpeedMbps());
        entity.setMediaType(request.getMediaType());
        entity.setAdminState(request.getAdminState());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(PortUpdateRequest request, Port entity) {
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
        if (request.getDeviceId() != null) {
            entity.setDeviceId(request.getDeviceId());
        }
        if (request.getCardId() != null) {
            entity.setCardId(request.getCardId());
        }
        if (request.getPortNumber() != null) {
            entity.setPortNumber(request.getPortNumber());
        }
        if (request.getSpeedMbps() != null) {
            entity.setSpeedMbps(request.getSpeedMbps());
        }
        if (request.getMediaType() != null) {
            entity.setMediaType(request.getMediaType());
        }
        if (request.getAdminState() != null) {
            entity.setAdminState(request.getAdminState());
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

    public PortResponse toResponse(Port entity) {
        PortResponse response = new PortResponse();
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
        response.setDeviceId(entity.getDeviceId());
        response.setCardId(entity.getCardId());
        response.setPortNumber(entity.getPortNumber());
        response.setSpeedMbps(entity.getSpeedMbps());
        response.setMediaType(entity.getMediaType());
        response.setAdminState(entity.getAdminState());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public PortSummary toSummary(Port entity) {
        return new PortSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PortResponse> toResponses(List<Port> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Port entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getDeviceId(),
                entity.getCardId(),
                entity.getPortNumber(),
                entity.getSpeedMbps(),
                entity.getMediaType(),
                entity.getAdminState(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
