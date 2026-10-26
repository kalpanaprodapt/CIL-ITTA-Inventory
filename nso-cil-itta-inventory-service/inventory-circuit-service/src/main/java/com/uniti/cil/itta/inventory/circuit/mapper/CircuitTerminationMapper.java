package com.uniti.cil.itta.inventory.circuit.mapper;

import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.CircuitTermination;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CircuitTermination entities and DTOs (kept explicit for auditability). */
@Component
public class CircuitTerminationMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "circuit_id", "side", "port_id", "xconnect_id", "patch_info", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public CircuitTermination toEntity(CircuitTerminationCreateRequest request) {
        CircuitTermination entity = new CircuitTermination();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCircuitId(request.getCircuitId());
        entity.setSide(request.getSide());
        entity.setPortId(request.getPortId());
        entity.setXconnectId(request.getXconnectId());
        entity.setPatchInfo(request.getPatchInfo());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(CircuitTerminationUpdateRequest request, CircuitTermination entity) {
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
        if (request.getSide() != null) {
            entity.setSide(request.getSide());
        }
        if (request.getPortId() != null) {
            entity.setPortId(request.getPortId());
        }
        if (request.getXconnectId() != null) {
            entity.setXconnectId(request.getXconnectId());
        }
        if (request.getPatchInfo() != null) {
            entity.setPatchInfo(request.getPatchInfo());
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

    public CircuitTerminationResponse toResponse(CircuitTermination entity) {
        CircuitTerminationResponse response = new CircuitTerminationResponse();
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
        response.setSide(entity.getSide());
        response.setPortId(entity.getPortId());
        response.setXconnectId(entity.getXconnectId());
        response.setPatchInfo(entity.getPatchInfo());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public CircuitTerminationSummary toSummary(CircuitTermination entity) {
        return new CircuitTerminationSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CircuitTerminationResponse> toResponses(List<CircuitTermination> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CircuitTermination entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCircuitId(),
                entity.getSide(),
                entity.getPortId(),
                entity.getXconnectId(),
                entity.getPatchInfo(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
