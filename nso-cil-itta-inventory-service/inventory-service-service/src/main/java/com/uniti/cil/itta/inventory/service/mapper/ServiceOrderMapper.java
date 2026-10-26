package com.uniti.cil.itta.inventory.service.mapper;

import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.ServiceOrder;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ServiceOrder entities and DTOs (kept explicit for auditability). */
@Component
public class ServiceOrderMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "customer_id", "order_type", "requested_on", "target_date", "priority", "region", "owner", "criticality", "cost_center", "notes");

    public ServiceOrder toEntity(ServiceOrderCreateRequest request) {
        ServiceOrder entity = new ServiceOrder();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCustomerId(request.getCustomerId());
        entity.setOrderType(request.getOrderType());
        entity.setRequestedOn(request.getRequestedOn());
        entity.setTargetDate(request.getTargetDate());
        entity.setPriority(request.getPriority());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(ServiceOrderUpdateRequest request, ServiceOrder entity) {
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
        if (request.getCustomerId() != null) {
            entity.setCustomerId(request.getCustomerId());
        }
        if (request.getOrderType() != null) {
            entity.setOrderType(request.getOrderType());
        }
        if (request.getRequestedOn() != null) {
            entity.setRequestedOn(request.getRequestedOn());
        }
        if (request.getTargetDate() != null) {
            entity.setTargetDate(request.getTargetDate());
        }
        if (request.getPriority() != null) {
            entity.setPriority(request.getPriority());
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

    public ServiceOrderResponse toResponse(ServiceOrder entity) {
        ServiceOrderResponse response = new ServiceOrderResponse();
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
        response.setCustomerId(entity.getCustomerId());
        response.setOrderType(entity.getOrderType());
        response.setRequestedOn(entity.getRequestedOn());
        response.setTargetDate(entity.getTargetDate());
        response.setPriority(entity.getPriority());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public ServiceOrderSummary toSummary(ServiceOrder entity) {
        return new ServiceOrderSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ServiceOrderResponse> toResponses(List<ServiceOrder> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ServiceOrder entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCustomerId(),
                entity.getOrderType(),
                entity.getRequestedOn(),
                entity.getTargetDate(),
                entity.getPriority(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
