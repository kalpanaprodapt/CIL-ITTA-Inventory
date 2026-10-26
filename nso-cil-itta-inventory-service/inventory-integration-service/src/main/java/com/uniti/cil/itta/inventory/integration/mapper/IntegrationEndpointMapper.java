package com.uniti.cil.itta.inventory.integration.mapper;

import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.IntegrationEndpoint;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between IntegrationEndpoint entities and DTOs (kept explicit for auditability). */
@Component
public class IntegrationEndpointMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "endpoint_name", "target_system", "base_url", "auth_type", "timeout_ms", "source_system", "region", "owner", "criticality", "cost_center");

    public IntegrationEndpoint toEntity(IntegrationEndpointCreateRequest request) {
        IntegrationEndpoint entity = new IntegrationEndpoint();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setEndpointName(request.getEndpointName());
        entity.setTargetSystem(request.getTargetSystem());
        entity.setBaseUrl(request.getBaseUrl());
        entity.setAuthType(request.getAuthType());
        entity.setTimeoutMs(request.getTimeoutMs());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(IntegrationEndpointUpdateRequest request, IntegrationEndpoint entity) {
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
        if (request.getEndpointName() != null) {
            entity.setEndpointName(request.getEndpointName());
        }
        if (request.getTargetSystem() != null) {
            entity.setTargetSystem(request.getTargetSystem());
        }
        if (request.getBaseUrl() != null) {
            entity.setBaseUrl(request.getBaseUrl());
        }
        if (request.getAuthType() != null) {
            entity.setAuthType(request.getAuthType());
        }
        if (request.getTimeoutMs() != null) {
            entity.setTimeoutMs(request.getTimeoutMs());
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
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public IntegrationEndpointResponse toResponse(IntegrationEndpoint entity) {
        IntegrationEndpointResponse response = new IntegrationEndpointResponse();
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
        response.setEndpointName(entity.getEndpointName());
        response.setTargetSystem(entity.getTargetSystem());
        response.setBaseUrl(entity.getBaseUrl());
        response.setAuthType(entity.getAuthType());
        response.setTimeoutMs(entity.getTimeoutMs());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public IntegrationEndpointSummary toSummary(IntegrationEndpoint entity) {
        return new IntegrationEndpointSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<IntegrationEndpointResponse> toResponses(List<IntegrationEndpoint> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(IntegrationEndpoint entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getEndpointName(),
                entity.getTargetSystem(),
                entity.getBaseUrl(),
                entity.getAuthType(),
                entity.getTimeoutMs(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
