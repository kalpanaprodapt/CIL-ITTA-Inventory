package com.uniti.cil.itta.inventory.software.mapper;

import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.SoftwareImage;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SoftwareImage entities and DTOs (kept explicit for auditability). */
@Component
public class SoftwareImageMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vendor_id", "image_name", "size_mb", "checksum", "platform", "storage_path", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public SoftwareImage toEntity(SoftwareImageCreateRequest request) {
        SoftwareImage entity = new SoftwareImage();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVendorId(request.getVendorId());
        entity.setImageName(request.getImageName());
        entity.setSizeMb(request.getSizeMb());
        entity.setChecksum(request.getChecksum());
        entity.setPlatform(request.getPlatform());
        entity.setStoragePath(request.getStoragePath());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(SoftwareImageUpdateRequest request, SoftwareImage entity) {
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
        if (request.getImageName() != null) {
            entity.setImageName(request.getImageName());
        }
        if (request.getSizeMb() != null) {
            entity.setSizeMb(request.getSizeMb());
        }
        if (request.getChecksum() != null) {
            entity.setChecksum(request.getChecksum());
        }
        if (request.getPlatform() != null) {
            entity.setPlatform(request.getPlatform());
        }
        if (request.getStoragePath() != null) {
            entity.setStoragePath(request.getStoragePath());
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

    public SoftwareImageResponse toResponse(SoftwareImage entity) {
        SoftwareImageResponse response = new SoftwareImageResponse();
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
        response.setImageName(entity.getImageName());
        response.setSizeMb(entity.getSizeMb());
        response.setChecksum(entity.getChecksum());
        response.setPlatform(entity.getPlatform());
        response.setStoragePath(entity.getStoragePath());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public SoftwareImageSummary toSummary(SoftwareImage entity) {
        return new SoftwareImageSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SoftwareImageResponse> toResponses(List<SoftwareImage> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SoftwareImage entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVendorId(),
                entity.getImageName(),
                entity.getSizeMb(),
                entity.getChecksum(),
                entity.getPlatform(),
                entity.getStoragePath(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
