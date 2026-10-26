package com.uniti.cil.itta.inventory.circuit.mapper;

import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.Circuit;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Circuit entities and DTOs (kept explicit for auditability). */
@Component
public class CircuitMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "circuit_id", "circuit_type", "bandwidth_mbps", "provider_id", "a_site_id", "z_site_id", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public Circuit toEntity(CircuitCreateRequest request) {
        Circuit entity = new Circuit();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCircuitId(request.getCircuitId());
        entity.setCircuitType(request.getCircuitType());
        entity.setBandwidthMbps(request.getBandwidthMbps());
        entity.setProviderId(request.getProviderId());
        entity.setASiteId(request.getASiteId());
        entity.setZSiteId(request.getZSiteId());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(CircuitUpdateRequest request, Circuit entity) {
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
        if (request.getCircuitId() != null) {
            entity.setCircuitId(request.getCircuitId());
        }
        if (request.getCircuitType() != null) {
            entity.setCircuitType(request.getCircuitType());
        }
        if (request.getBandwidthMbps() != null) {
            entity.setBandwidthMbps(request.getBandwidthMbps());
        }
        if (request.getProviderId() != null) {
            entity.setProviderId(request.getProviderId());
        }
        if (request.getASiteId() != null) {
            entity.setASiteId(request.getASiteId());
        }
        if (request.getZSiteId() != null) {
            entity.setZSiteId(request.getZSiteId());
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

    public CircuitResponse toResponse(Circuit entity) {
        CircuitResponse response = new CircuitResponse();
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
        response.setCircuitId(entity.getCircuitId());
        response.setCircuitType(entity.getCircuitType());
        response.setBandwidthMbps(entity.getBandwidthMbps());
        response.setProviderId(entity.getProviderId());
        response.setASiteId(entity.getASiteId());
        response.setZSiteId(entity.getZSiteId());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public CircuitSummary toSummary(Circuit entity) {
        return new CircuitSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CircuitResponse> toResponses(List<Circuit> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Circuit entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCircuitId(),
                entity.getCircuitType(),
                entity.getBandwidthMbps(),
                entity.getProviderId(),
                entity.getASiteId(),
                entity.getZSiteId(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
