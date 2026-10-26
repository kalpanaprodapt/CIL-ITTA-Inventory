package com.uniti.cil.itta.inventory.change.mapper;

import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.WorkOrder;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between WorkOrder entities and DTOs (kept explicit for auditability). */
@Component
public class WorkOrderMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "work_order_number", "work_type", "assigned_technician_id", "due_date", "work_state", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public WorkOrder toEntity(WorkOrderCreateRequest request) {
        WorkOrder entity = new WorkOrder();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setWorkOrderNumber(request.getWorkOrderNumber());
        entity.setWorkType(request.getWorkType());
        entity.setAssignedTechnicianId(request.getAssignedTechnicianId());
        entity.setDueDate(request.getDueDate());
        entity.setWorkState(request.getWorkState());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(WorkOrderUpdateRequest request, WorkOrder entity) {
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
        if (request.getWorkOrderNumber() != null) {
            entity.setWorkOrderNumber(request.getWorkOrderNumber());
        }
        if (request.getWorkType() != null) {
            entity.setWorkType(request.getWorkType());
        }
        if (request.getAssignedTechnicianId() != null) {
            entity.setAssignedTechnicianId(request.getAssignedTechnicianId());
        }
        if (request.getDueDate() != null) {
            entity.setDueDate(request.getDueDate());
        }
        if (request.getWorkState() != null) {
            entity.setWorkState(request.getWorkState());
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

    public WorkOrderResponse toResponse(WorkOrder entity) {
        WorkOrderResponse response = new WorkOrderResponse();
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
        response.setWorkOrderNumber(entity.getWorkOrderNumber());
        response.setWorkType(entity.getWorkType());
        response.setAssignedTechnicianId(entity.getAssignedTechnicianId());
        response.setDueDate(entity.getDueDate());
        response.setWorkState(entity.getWorkState());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public WorkOrderSummary toSummary(WorkOrder entity) {
        return new WorkOrderSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<WorkOrderResponse> toResponses(List<WorkOrder> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(WorkOrder entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getWorkOrderNumber(),
                entity.getWorkType(),
                entity.getAssignedTechnicianId(),
                entity.getDueDate(),
                entity.getWorkState(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
