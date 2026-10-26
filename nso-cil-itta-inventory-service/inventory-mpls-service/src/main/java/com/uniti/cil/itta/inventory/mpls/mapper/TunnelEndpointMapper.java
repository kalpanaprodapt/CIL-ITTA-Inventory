package com.uniti.cil.itta.inventory.mpls.mapper;

import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.TunnelEndpoint;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between TunnelEndpoint entities and DTOs (kept explicit for auditability). */
@Component
public class TunnelEndpointMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "device_id", "tunnel_name", "tunnel_type", "source_ip", "destination_ip", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public TunnelEndpoint toEntity(TunnelEndpointCreateRequest request) {
        TunnelEndpoint entity = new TunnelEndpoint();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setDeviceId(request.getDeviceId());
        entity.setTunnelName(request.getTunnelName());
        entity.setTunnelType(request.getTunnelType());
        entity.setSourceIp(request.getSourceIp());
        entity.setDestinationIp(request.getDestinationIp());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(TunnelEndpointUpdateRequest request, TunnelEndpoint entity) {
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
        if (request.getTunnelName() != null) {
            entity.setTunnelName(request.getTunnelName());
        }
        if (request.getTunnelType() != null) {
            entity.setTunnelType(request.getTunnelType());
        }
        if (request.getSourceIp() != null) {
            entity.setSourceIp(request.getSourceIp());
        }
        if (request.getDestinationIp() != null) {
            entity.setDestinationIp(request.getDestinationIp());
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

    public TunnelEndpointResponse toResponse(TunnelEndpoint entity) {
        TunnelEndpointResponse response = new TunnelEndpointResponse();
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
        response.setTunnelName(entity.getTunnelName());
        response.setTunnelType(entity.getTunnelType());
        response.setSourceIp(entity.getSourceIp());
        response.setDestinationIp(entity.getDestinationIp());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public TunnelEndpointSummary toSummary(TunnelEndpoint entity) {
        return new TunnelEndpointSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TunnelEndpointResponse> toResponses(List<TunnelEndpoint> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(TunnelEndpoint entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getDeviceId(),
                entity.getTunnelName(),
                entity.getTunnelType(),
                entity.getSourceIp(),
                entity.getDestinationIp(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
