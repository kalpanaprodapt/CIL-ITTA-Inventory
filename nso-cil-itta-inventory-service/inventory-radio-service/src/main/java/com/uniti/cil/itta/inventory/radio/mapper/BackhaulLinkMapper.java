package com.uniti.cil.itta.inventory.radio.mapper;

import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.BackhaulLink;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between BackhaulLink entities and DTOs (kept explicit for auditability). */
@Component
public class BackhaulLinkMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "base_station_id", "link_medium", "capacity_mbps", "latency_ms", "circuit_ref", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public BackhaulLink toEntity(BackhaulLinkCreateRequest request) {
        BackhaulLink entity = new BackhaulLink();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setBaseStationId(request.getBaseStationId());
        entity.setLinkMedium(request.getLinkMedium());
        entity.setCapacityMbps(request.getCapacityMbps());
        entity.setLatencyMs(request.getLatencyMs());
        entity.setCircuitRef(request.getCircuitRef());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(BackhaulLinkUpdateRequest request, BackhaulLink entity) {
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
        if (request.getBaseStationId() != null) {
            entity.setBaseStationId(request.getBaseStationId());
        }
        if (request.getLinkMedium() != null) {
            entity.setLinkMedium(request.getLinkMedium());
        }
        if (request.getCapacityMbps() != null) {
            entity.setCapacityMbps(request.getCapacityMbps());
        }
        if (request.getLatencyMs() != null) {
            entity.setLatencyMs(request.getLatencyMs());
        }
        if (request.getCircuitRef() != null) {
            entity.setCircuitRef(request.getCircuitRef());
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
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
    }

    public BackhaulLinkResponse toResponse(BackhaulLink entity) {
        BackhaulLinkResponse response = new BackhaulLinkResponse();
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
        response.setBaseStationId(entity.getBaseStationId());
        response.setLinkMedium(entity.getLinkMedium());
        response.setCapacityMbps(entity.getCapacityMbps());
        response.setLatencyMs(entity.getLatencyMs());
        response.setCircuitRef(entity.getCircuitRef());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public BackhaulLinkSummary toSummary(BackhaulLink entity) {
        return new BackhaulLinkSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<BackhaulLinkResponse> toResponses(List<BackhaulLink> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(BackhaulLink entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getBaseStationId(),
                entity.getLinkMedium(),
                entity.getCapacityMbps(),
                entity.getLatencyMs(),
                entity.getCircuitRef(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
