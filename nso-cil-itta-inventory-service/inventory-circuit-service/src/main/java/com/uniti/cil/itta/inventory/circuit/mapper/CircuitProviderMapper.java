package com.uniti.cil.itta.inventory.circuit.mapper;

import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.CircuitProvider;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CircuitProvider entities and DTOs (kept explicit for auditability). */
@Component
public class CircuitProviderMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "provider_name", "account_number", "noc", "escalation_email", "provider_type", "owner", "criticality", "cost_center", "notes", "effective_from");

    public CircuitProvider toEntity(CircuitProviderCreateRequest request) {
        CircuitProvider entity = new CircuitProvider();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setProviderName(request.getProviderName());
        entity.setAccountNumber(request.getAccountNumber());
        entity.setNoc(request.getNoc());
        entity.setEscalationEmail(request.getEscalationEmail());
        entity.setProviderType(request.getProviderType());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(CircuitProviderUpdateRequest request, CircuitProvider entity) {
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
        if (request.getProviderName() != null) {
            entity.setProviderName(request.getProviderName());
        }
        if (request.getAccountNumber() != null) {
            entity.setAccountNumber(request.getAccountNumber());
        }
        if (request.getNoc() != null) {
            entity.setNoc(request.getNoc());
        }
        if (request.getEscalationEmail() != null) {
            entity.setEscalationEmail(request.getEscalationEmail());
        }
        if (request.getProviderType() != null) {
            entity.setProviderType(request.getProviderType());
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

    public CircuitProviderResponse toResponse(CircuitProvider entity) {
        CircuitProviderResponse response = new CircuitProviderResponse();
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
        response.setProviderName(entity.getProviderName());
        response.setAccountNumber(entity.getAccountNumber());
        response.setNoc(entity.getNoc());
        response.setEscalationEmail(entity.getEscalationEmail());
        response.setProviderType(entity.getProviderType());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public CircuitProviderSummary toSummary(CircuitProvider entity) {
        return new CircuitProviderSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CircuitProviderResponse> toResponses(List<CircuitProvider> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CircuitProvider entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getProviderName(),
                entity.getAccountNumber(),
                entity.getNoc(),
                entity.getEscalationEmail(),
                entity.getProviderType(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
