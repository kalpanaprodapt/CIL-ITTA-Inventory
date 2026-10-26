package com.uniti.cil.itta.inventory.discovery.mapper;

import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryJob;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between DiscoveryJob entities and DTOs (kept explicit for auditability). */
@Component
public class DiscoveryJobMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "profile_id", "started_at", "finished_at", "job_state", "assets_found", "owner", "criticality", "cost_center", "notes", "effective_from");

    public DiscoveryJob toEntity(DiscoveryJobCreateRequest request) {
        DiscoveryJob entity = new DiscoveryJob();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setProfileId(request.getProfileId());
        entity.setStartedAt(request.getStartedAt());
        entity.setFinishedAt(request.getFinishedAt());
        entity.setJobState(request.getJobState());
        entity.setAssetsFound(request.getAssetsFound());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(DiscoveryJobUpdateRequest request, DiscoveryJob entity) {
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
        if (request.getProfileId() != null) {
            entity.setProfileId(request.getProfileId());
        }
        if (request.getStartedAt() != null) {
            entity.setStartedAt(request.getStartedAt());
        }
        if (request.getFinishedAt() != null) {
            entity.setFinishedAt(request.getFinishedAt());
        }
        if (request.getJobState() != null) {
            entity.setJobState(request.getJobState());
        }
        if (request.getAssetsFound() != null) {
            entity.setAssetsFound(request.getAssetsFound());
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

    public DiscoveryJobResponse toResponse(DiscoveryJob entity) {
        DiscoveryJobResponse response = new DiscoveryJobResponse();
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
        response.setProfileId(entity.getProfileId());
        response.setStartedAt(entity.getStartedAt());
        response.setFinishedAt(entity.getFinishedAt());
        response.setJobState(entity.getJobState());
        response.setAssetsFound(entity.getAssetsFound());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public DiscoveryJobSummary toSummary(DiscoveryJob entity) {
        return new DiscoveryJobSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<DiscoveryJobResponse> toResponses(List<DiscoveryJob> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(DiscoveryJob entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getProfileId(),
                entity.getStartedAt(),
                entity.getFinishedAt(),
                entity.getJobState(),
                entity.getAssetsFound(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
