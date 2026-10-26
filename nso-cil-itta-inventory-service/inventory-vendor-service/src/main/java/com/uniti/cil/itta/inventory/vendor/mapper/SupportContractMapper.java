package com.uniti.cil.itta.inventory.vendor.mapper;

import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.SupportContract;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SupportContract entities and DTOs (kept explicit for auditability). */
@Component
public class SupportContractMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vendor_id", "contract_number", "start_date", "end_date", "coverage_level", "annual_cost", "owner", "criticality", "cost_center", "notes", "effective_from");

    public SupportContract toEntity(SupportContractCreateRequest request) {
        SupportContract entity = new SupportContract();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVendorId(request.getVendorId());
        entity.setContractNumber(request.getContractNumber());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setCoverageLevel(request.getCoverageLevel());
        entity.setAnnualCost(request.getAnnualCost());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(SupportContractUpdateRequest request, SupportContract entity) {
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
        if (request.getVendorId() != null) {
            entity.setVendorId(request.getVendorId());
        }
        if (request.getContractNumber() != null) {
            entity.setContractNumber(request.getContractNumber());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getCoverageLevel() != null) {
            entity.setCoverageLevel(request.getCoverageLevel());
        }
        if (request.getAnnualCost() != null) {
            entity.setAnnualCost(request.getAnnualCost());
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

    public SupportContractResponse toResponse(SupportContract entity) {
        SupportContractResponse response = new SupportContractResponse();
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
        response.setVendorId(entity.getVendorId());
        response.setContractNumber(entity.getContractNumber());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setCoverageLevel(entity.getCoverageLevel());
        response.setAnnualCost(entity.getAnnualCost());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public SupportContractSummary toSummary(SupportContract entity) {
        return new SupportContractSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SupportContractResponse> toResponses(List<SupportContract> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SupportContract entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVendorId(),
                entity.getContractNumber(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getCoverageLevel(),
                entity.getAnnualCost(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
