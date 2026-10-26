package com.uniti.cil.itta.inventory.software.mapper;

import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.LicensePool;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between LicensePool entities and DTOs (kept explicit for auditability). */
@Component
public class LicensePoolMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vendor_id", "pool_name", "total_seats", "used_seats", "renewal_date", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public LicensePool toEntity(LicensePoolCreateRequest request) {
        LicensePool entity = new LicensePool();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVendorId(request.getVendorId());
        entity.setPoolName(request.getPoolName());
        entity.setTotalSeats(request.getTotalSeats());
        entity.setUsedSeats(request.getUsedSeats());
        entity.setRenewalDate(request.getRenewalDate());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(LicensePoolUpdateRequest request, LicensePool entity) {
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
        if (request.getPoolName() != null) {
            entity.setPoolName(request.getPoolName());
        }
        if (request.getTotalSeats() != null) {
            entity.setTotalSeats(request.getTotalSeats());
        }
        if (request.getUsedSeats() != null) {
            entity.setUsedSeats(request.getUsedSeats());
        }
        if (request.getRenewalDate() != null) {
            entity.setRenewalDate(request.getRenewalDate());
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
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
    }

    public LicensePoolResponse toResponse(LicensePool entity) {
        LicensePoolResponse response = new LicensePoolResponse();
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
        response.setPoolName(entity.getPoolName());
        response.setTotalSeats(entity.getTotalSeats());
        response.setUsedSeats(entity.getUsedSeats());
        response.setRenewalDate(entity.getRenewalDate());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public LicensePoolSummary toSummary(LicensePool entity) {
        return new LicensePoolSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<LicensePoolResponse> toResponses(List<LicensePool> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(LicensePool entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVendorId(),
                entity.getPoolName(),
                entity.getTotalSeats(),
                entity.getUsedSeats(),
                entity.getRenewalDate(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
