package com.uniti.cil.itta.inventory.integration.mapper;

import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.SyncJob;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SyncJob entities and DTOs (kept explicit for auditability). */
@Component
public class SyncJobMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "endpoint_id", "direction", "schedule_cron", "last_run_at", "last_status", "owner", "criticality", "cost_center", "notes", "effective_from");

    public SyncJob toEntity(SyncJobCreateRequest request) {
        SyncJob entity = new SyncJob();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setEndpointId(request.getEndpointId());
        entity.setDirection(request.getDirection());
        entity.setScheduleCron(request.getScheduleCron());
        entity.setLastRunAt(request.getLastRunAt());
        entity.setLastStatus(request.getLastStatus());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(SyncJobUpdateRequest request, SyncJob entity) {
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
        if (request.getEndpointId() != null) {
            entity.setEndpointId(request.getEndpointId());
        }
        if (request.getDirection() != null) {
            entity.setDirection(request.getDirection());
        }
        if (request.getScheduleCron() != null) {
            entity.setScheduleCron(request.getScheduleCron());
        }
        if (request.getLastRunAt() != null) {
            entity.setLastRunAt(request.getLastRunAt());
        }
        if (request.getLastStatus() != null) {
            entity.setLastStatus(request.getLastStatus());
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

    public SyncJobResponse toResponse(SyncJob entity) {
        SyncJobResponse response = new SyncJobResponse();
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
        response.setEndpointId(entity.getEndpointId());
        response.setDirection(entity.getDirection());
        response.setScheduleCron(entity.getScheduleCron());
        response.setLastRunAt(entity.getLastRunAt());
        response.setLastStatus(entity.getLastStatus());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public SyncJobSummary toSummary(SyncJob entity) {
        return new SyncJobSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SyncJobResponse> toResponses(List<SyncJob> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SyncJob entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getEndpointId(),
                entity.getDirection(),
                entity.getScheduleCron(),
                entity.getLastRunAt(),
                entity.getLastStatus(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
