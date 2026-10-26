package com.uniti.cil.itta.inventory.discovery.mapper;

import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveryProfile;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between DiscoveryProfile entities and DTOs (kept explicit for auditability). */
@Component
public class DiscoveryProfileMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "profile_name", "protocol", "credential_ref", "scope_cidr", "schedule_cron", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public DiscoveryProfile toEntity(DiscoveryProfileCreateRequest request) {
        DiscoveryProfile entity = new DiscoveryProfile();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setProfileName(request.getProfileName());
        entity.setProtocol(request.getProtocol());
        entity.setCredentialRef(request.getCredentialRef());
        entity.setScopeCidr(request.getScopeCidr());
        entity.setScheduleCron(request.getScheduleCron());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(DiscoveryProfileUpdateRequest request, DiscoveryProfile entity) {
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
        if (request.getProtocol() != null) {
            entity.setProtocol(request.getProtocol());
        }
        if (request.getCredentialRef() != null) {
            entity.setCredentialRef(request.getCredentialRef());
        }
        if (request.getScopeCidr() != null) {
            entity.setScopeCidr(request.getScopeCidr());
        }
        if (request.getScheduleCron() != null) {
            entity.setScheduleCron(request.getScheduleCron());
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

    public DiscoveryProfileResponse toResponse(DiscoveryProfile entity) {
        DiscoveryProfileResponse response = new DiscoveryProfileResponse();
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
        response.setProtocol(entity.getProtocol());
        response.setCredentialRef(entity.getCredentialRef());
        response.setScopeCidr(entity.getScopeCidr());
        response.setScheduleCron(entity.getScheduleCron());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public DiscoveryProfileSummary toSummary(DiscoveryProfile entity) {
        return new DiscoveryProfileSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<DiscoveryProfileResponse> toResponses(List<DiscoveryProfile> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(DiscoveryProfile entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getProfileName(),
                entity.getProtocol(),
                entity.getCredentialRef(),
                entity.getScopeCidr(),
                entity.getScheduleCron(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
