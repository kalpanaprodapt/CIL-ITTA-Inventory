package com.uniti.cil.itta.inventory.software.mapper;

import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.SoftwareVersion;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SoftwareVersion entities and DTOs (kept explicit for auditability). */
@Component
public class SoftwareVersionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "image_id", "version_string", "release_date", "end_of_support", "release_train", "cve_count", "region", "owner", "criticality", "cost_center", "notes");

    public SoftwareVersion toEntity(SoftwareVersionCreateRequest request) {
        SoftwareVersion entity = new SoftwareVersion();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setImageId(request.getImageId());
        entity.setVersionString(request.getVersionString());
        entity.setReleaseDate(request.getReleaseDate());
        entity.setEndOfSupport(request.getEndOfSupport());
        entity.setReleaseTrain(request.getReleaseTrain());
        entity.setCveCount(request.getCveCount());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(SoftwareVersionUpdateRequest request, SoftwareVersion entity) {
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
        if (request.getImageId() != null) {
            entity.setImageId(request.getImageId());
        }
        if (request.getVersionString() != null) {
            entity.setVersionString(request.getVersionString());
        }
        if (request.getReleaseDate() != null) {
            entity.setReleaseDate(request.getReleaseDate());
        }
        if (request.getEndOfSupport() != null) {
            entity.setEndOfSupport(request.getEndOfSupport());
        }
        if (request.getReleaseTrain() != null) {
            entity.setReleaseTrain(request.getReleaseTrain());
        }
        if (request.getCveCount() != null) {
            entity.setCveCount(request.getCveCount());
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

    public SoftwareVersionResponse toResponse(SoftwareVersion entity) {
        SoftwareVersionResponse response = new SoftwareVersionResponse();
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
        response.setImageId(entity.getImageId());
        response.setVersionString(entity.getVersionString());
        response.setReleaseDate(entity.getReleaseDate());
        response.setEndOfSupport(entity.getEndOfSupport());
        response.setReleaseTrain(entity.getReleaseTrain());
        response.setCveCount(entity.getCveCount());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public SoftwareVersionSummary toSummary(SoftwareVersion entity) {
        return new SoftwareVersionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SoftwareVersionResponse> toResponses(List<SoftwareVersion> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SoftwareVersion entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getImageId(),
                entity.getVersionString(),
                entity.getReleaseDate(),
                entity.getEndOfSupport(),
                entity.getReleaseTrain(),
                entity.getCveCount(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
