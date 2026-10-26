package com.uniti.cil.itta.inventory.circuit.mapper;

import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.CircuitOrder;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CircuitOrder entities and DTOs (kept explicit for auditability). */
@Component
public class CircuitOrderMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "circuit_id", "order_number", "ordered_on", "requested_date", "committed_date", "order_status", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public CircuitOrder toEntity(CircuitOrderCreateRequest request) {
        CircuitOrder entity = new CircuitOrder();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCircuitId(request.getCircuitId());
        entity.setOrderNumber(request.getOrderNumber());
        entity.setOrderedOn(request.getOrderedOn());
        entity.setRequestedDate(request.getRequestedDate());
        entity.setCommittedDate(request.getCommittedDate());
        entity.setOrderStatus(request.getOrderStatus());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(CircuitOrderUpdateRequest request, CircuitOrder entity) {
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
        if (request.getOrderNumber() != null) {
            entity.setOrderNumber(request.getOrderNumber());
        }
        if (request.getOrderedOn() != null) {
            entity.setOrderedOn(request.getOrderedOn());
        }
        if (request.getRequestedDate() != null) {
            entity.setRequestedDate(request.getRequestedDate());
        }
        if (request.getCommittedDate() != null) {
            entity.setCommittedDate(request.getCommittedDate());
        }
        if (request.getOrderStatus() != null) {
            entity.setOrderStatus(request.getOrderStatus());
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

    public CircuitOrderResponse toResponse(CircuitOrder entity) {
        CircuitOrderResponse response = new CircuitOrderResponse();
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
        response.setOrderNumber(entity.getOrderNumber());
        response.setOrderedOn(entity.getOrderedOn());
        response.setRequestedDate(entity.getRequestedDate());
        response.setCommittedDate(entity.getCommittedDate());
        response.setOrderStatus(entity.getOrderStatus());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public CircuitOrderSummary toSummary(CircuitOrder entity) {
        return new CircuitOrderSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CircuitOrderResponse> toResponses(List<CircuitOrder> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CircuitOrder entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCircuitId(),
                entity.getOrderNumber(),
                entity.getOrderedOn(),
                entity.getRequestedDate(),
                entity.getCommittedDate(),
                entity.getOrderStatus(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
