package com.uniti.cil.itta.inventory.customer.mapper;

import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.SlaProfile;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SlaProfile entities and DTOs (kept explicit for auditability). */
@Component
public class SlaProfileMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "profile_name", "availability_pct", "mttr_minutes", "latency_ms", "jitter_ms", "credit_policy", "owner", "criticality", "cost_center", "notes", "effective_from");

    public SlaProfile toEntity(SlaProfileCreateRequest request) {
        SlaProfile entity = new SlaProfile();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setProfileName(request.getProfileName());
        entity.setAvailabilityPct(request.getAvailabilityPct());
        entity.setMttrMinutes(request.getMttrMinutes());
        entity.setLatencyMs(request.getLatencyMs());
        entity.setJitterMs(request.getJitterMs());
        entity.setCreditPolicy(request.getCreditPolicy());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(SlaProfileUpdateRequest request, SlaProfile entity) {
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
        if (request.getProfileName() != null) {
            entity.setProfileName(request.getProfileName());
        }
        if (request.getAvailabilityPct() != null) {
            entity.setAvailabilityPct(request.getAvailabilityPct());
        }
        if (request.getMttrMinutes() != null) {
            entity.setMttrMinutes(request.getMttrMinutes());
        }
        if (request.getLatencyMs() != null) {
            entity.setLatencyMs(request.getLatencyMs());
        }
        if (request.getJitterMs() != null) {
            entity.setJitterMs(request.getJitterMs());
        }
        if (request.getCreditPolicy() != null) {
            entity.setCreditPolicy(request.getCreditPolicy());
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

    public SlaProfileResponse toResponse(SlaProfile entity) {
        SlaProfileResponse response = new SlaProfileResponse();
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
        response.setProfileName(entity.getProfileName());
        response.setAvailabilityPct(entity.getAvailabilityPct());
        response.setMttrMinutes(entity.getMttrMinutes());
        response.setLatencyMs(entity.getLatencyMs());
        response.setJitterMs(entity.getJitterMs());
        response.setCreditPolicy(entity.getCreditPolicy());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public SlaProfileSummary toSummary(SlaProfile entity) {
        return new SlaProfileSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SlaProfileResponse> toResponses(List<SlaProfile> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SlaProfile entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getProfileName(),
                entity.getAvailabilityPct(),
                entity.getMttrMinutes(),
                entity.getLatencyMs(),
                entity.getJitterMs(),
                entity.getCreditPolicy(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
