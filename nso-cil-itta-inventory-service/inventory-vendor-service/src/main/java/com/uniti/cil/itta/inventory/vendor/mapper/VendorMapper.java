package com.uniti.cil.itta.inventory.vendor.mapper;

import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.Vendor;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Vendor entities and DTOs (kept explicit for auditability). */
@Component
public class VendorMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "legal_name", "support_email", "support_phone", "website", "tier", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public Vendor toEntity(VendorCreateRequest request) {
        Vendor entity = new Vendor();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setLegalName(request.getLegalName());
        entity.setSupportEmail(request.getSupportEmail());
        entity.setSupportPhone(request.getSupportPhone());
        entity.setWebsite(request.getWebsite());
        entity.setTier(request.getTier());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(VendorUpdateRequest request, Vendor entity) {
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
        if (request.getLegalName() != null) {
            entity.setLegalName(request.getLegalName());
        }
        if (request.getSupportEmail() != null) {
            entity.setSupportEmail(request.getSupportEmail());
        }
        if (request.getSupportPhone() != null) {
            entity.setSupportPhone(request.getSupportPhone());
        }
        if (request.getWebsite() != null) {
            entity.setWebsite(request.getWebsite());
        }
        if (request.getTier() != null) {
            entity.setTier(request.getTier());
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

    public VendorResponse toResponse(Vendor entity) {
        VendorResponse response = new VendorResponse();
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
        response.setLegalName(entity.getLegalName());
        response.setSupportEmail(entity.getSupportEmail());
        response.setSupportPhone(entity.getSupportPhone());
        response.setWebsite(entity.getWebsite());
        response.setTier(entity.getTier());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public VendorSummary toSummary(Vendor entity) {
        return new VendorSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VendorResponse> toResponses(List<Vendor> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Vendor entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getLegalName(),
                entity.getSupportEmail(),
                entity.getSupportPhone(),
                entity.getWebsite(),
                entity.getTier(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
