package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.Invoice;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Invoice entities and DTOs (kept explicit for auditability). */
@Component
public class InvoiceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "invoice_number", "po_id", "amount", "due_date", "paid", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public Invoice toEntity(InvoiceCreateRequest request) {
        Invoice entity = new Invoice();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setInvoiceNumber(request.getInvoiceNumber());
        entity.setPoId(request.getPoId());
        entity.setAmount(request.getAmount());
        entity.setDueDate(request.getDueDate());
        entity.setPaid(request.getPaid());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(InvoiceUpdateRequest request, Invoice entity) {
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
        if (request.getInvoiceNumber() != null) {
            entity.setInvoiceNumber(request.getInvoiceNumber());
        }
        if (request.getPoId() != null) {
            entity.setPoId(request.getPoId());
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
        if (request.getDueDate() != null) {
            entity.setDueDate(request.getDueDate());
        }
        if (request.getPaid() != null) {
            entity.setPaid(request.getPaid());
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

    public InvoiceResponse toResponse(Invoice entity) {
        InvoiceResponse response = new InvoiceResponse();
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
        response.setInvoiceNumber(entity.getInvoiceNumber());
        response.setPoId(entity.getPoId());
        response.setAmount(entity.getAmount());
        response.setDueDate(entity.getDueDate());
        response.setPaid(entity.getPaid());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public InvoiceSummary toSummary(Invoice entity) {
        return new InvoiceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<InvoiceResponse> toResponses(List<Invoice> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Invoice entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getInvoiceNumber(),
                entity.getPoId(),
                entity.getAmount(),
                entity.getDueDate(),
                entity.getPaid(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
