package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.PurchaseOrder;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PurchaseOrder entities and DTOs (kept explicit for auditability). */
@Component
public class PurchaseOrderMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "po_number", "vendor_id", "ordered_on", "total_amount", "currency", "owner", "criticality", "cost_center", "notes", "effective_from");

    public PurchaseOrder toEntity(PurchaseOrderCreateRequest request) {
        PurchaseOrder entity = new PurchaseOrder();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPoNumber(request.getPoNumber());
        entity.setVendorId(request.getVendorId());
        entity.setOrderedOn(request.getOrderedOn());
        entity.setTotalAmount(request.getTotalAmount());
        entity.setCurrency(request.getCurrency());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(PurchaseOrderUpdateRequest request, PurchaseOrder entity) {
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
        if (request.getPoNumber() != null) {
            entity.setPoNumber(request.getPoNumber());
        }
        if (request.getVendorId() != null) {
            entity.setVendorId(request.getVendorId());
        }
        if (request.getOrderedOn() != null) {
            entity.setOrderedOn(request.getOrderedOn());
        }
        if (request.getTotalAmount() != null) {
            entity.setTotalAmount(request.getTotalAmount());
        }
        if (request.getCurrency() != null) {
            entity.setCurrency(request.getCurrency());
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
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
    }

    public PurchaseOrderResponse toResponse(PurchaseOrder entity) {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
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
        response.setPoNumber(entity.getPoNumber());
        response.setVendorId(entity.getVendorId());
        response.setOrderedOn(entity.getOrderedOn());
        response.setTotalAmount(entity.getTotalAmount());
        response.setCurrency(entity.getCurrency());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public PurchaseOrderSummary toSummary(PurchaseOrder entity) {
        return new PurchaseOrderSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PurchaseOrderResponse> toResponses(List<PurchaseOrder> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PurchaseOrder entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPoNumber(),
                entity.getVendorId(),
                entity.getOrderedOn(),
                entity.getTotalAmount(),
                entity.getCurrency(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
