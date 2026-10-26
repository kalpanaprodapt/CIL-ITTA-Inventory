package com.uniti.cil.itta.inventory.circuit.mapper;

import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.CircuitSegment;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CircuitSegment entities and DTOs (kept explicit for auditability). */
@Component
public class CircuitSegmentMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "circuit_id", "segment_order", "a_termination_id", "z_termination_id", "latency_ms", "source_system", "region", "owner", "criticality", "cost_center");

    public CircuitSegment toEntity(CircuitSegmentCreateRequest request) {
        CircuitSegment entity = new CircuitSegment();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCircuitId(request.getCircuitId());
        entity.setSegmentOrder(request.getSegmentOrder());
        entity.setATerminationId(request.getATerminationId());
        entity.setZTerminationId(request.getZTerminationId());
        entity.setLatencyMs(request.getLatencyMs());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(CircuitSegmentUpdateRequest request, CircuitSegment entity) {
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
        if (request.getSegmentOrder() != null) {
            entity.setSegmentOrder(request.getSegmentOrder());
        }
        if (request.getATerminationId() != null) {
            entity.setATerminationId(request.getATerminationId());
        }
        if (request.getZTerminationId() != null) {
            entity.setZTerminationId(request.getZTerminationId());
        }
        if (request.getLatencyMs() != null) {
            entity.setLatencyMs(request.getLatencyMs());
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

    public CircuitSegmentResponse toResponse(CircuitSegment entity) {
        CircuitSegmentResponse response = new CircuitSegmentResponse();
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
        response.setSegmentOrder(entity.getSegmentOrder());
        response.setATerminationId(entity.getATerminationId());
        response.setZTerminationId(entity.getZTerminationId());
        response.setLatencyMs(entity.getLatencyMs());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public CircuitSegmentSummary toSummary(CircuitSegment entity) {
        return new CircuitSegmentSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CircuitSegmentResponse> toResponses(List<CircuitSegment> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CircuitSegment entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCircuitId(),
                entity.getSegmentOrder(),
                entity.getATerminationId(),
                entity.getZTerminationId(),
                entity.getLatencyMs(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
