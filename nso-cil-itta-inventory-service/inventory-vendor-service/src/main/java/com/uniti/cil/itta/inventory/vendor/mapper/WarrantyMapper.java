package com.uniti.cil.itta.inventory.vendor.mapper;

import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.Warranty;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Warranty entities and DTOs (kept explicit for auditability). */
@Component
public class WarrantyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "asset_id", "start_date", "end_date", "warranty_type", "terms", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public Warranty toEntity(WarrantyCreateRequest request) {
        Warranty entity = new Warranty();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAssetId(request.getAssetId());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setWarrantyType(request.getWarrantyType());
        entity.setTerms(request.getTerms());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(WarrantyUpdateRequest request, Warranty entity) {
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
        if (request.getAssetId() != null) {
            entity.setAssetId(request.getAssetId());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getWarrantyType() != null) {
            entity.setWarrantyType(request.getWarrantyType());
        }
        if (request.getTerms() != null) {
            entity.setTerms(request.getTerms());
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

    public WarrantyResponse toResponse(Warranty entity) {
        WarrantyResponse response = new WarrantyResponse();
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
        response.setAssetId(entity.getAssetId());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setWarrantyType(entity.getWarrantyType());
        response.setTerms(entity.getTerms());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public WarrantySummary toSummary(Warranty entity) {
        return new WarrantySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<WarrantyResponse> toResponses(List<Warranty> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Warranty entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAssetId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getWarrantyType(),
                entity.getTerms(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
