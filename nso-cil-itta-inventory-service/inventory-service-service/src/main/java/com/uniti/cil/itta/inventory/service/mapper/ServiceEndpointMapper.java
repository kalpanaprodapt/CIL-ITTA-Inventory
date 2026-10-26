package com.uniti.cil.itta.inventory.service.mapper;

import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.ServiceEndpoint;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ServiceEndpoint entities and DTOs (kept explicit for auditability). */
@Component
public class ServiceEndpointMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "service_id", "site_id", "port_id", "endpoint_role", "handoff", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public ServiceEndpoint toEntity(ServiceEndpointCreateRequest request) {
        ServiceEndpoint entity = new ServiceEndpoint();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setServiceId(request.getServiceId());
        entity.setSiteId(request.getSiteId());
        entity.setPortId(request.getPortId());
        entity.setEndpointRole(request.getEndpointRole());
        entity.setHandoff(request.getHandoff());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(ServiceEndpointUpdateRequest request, ServiceEndpoint entity) {
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
        if (request.getServiceId() != null) {
            entity.setServiceId(request.getServiceId());
        }
        if (request.getSiteId() != null) {
            entity.setSiteId(request.getSiteId());
        }
        if (request.getPortId() != null) {
            entity.setPortId(request.getPortId());
        }
        if (request.getEndpointRole() != null) {
            entity.setEndpointRole(request.getEndpointRole());
        }
        if (request.getHandoff() != null) {
            entity.setHandoff(request.getHandoff());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
    }

    public ServiceEndpointResponse toResponse(ServiceEndpoint entity) {
        ServiceEndpointResponse response = new ServiceEndpointResponse();
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
        response.setServiceId(entity.getServiceId());
        response.setSiteId(entity.getSiteId());
        response.setPortId(entity.getPortId());
        response.setEndpointRole(entity.getEndpointRole());
        response.setHandoff(entity.getHandoff());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public ServiceEndpointSummary toSummary(ServiceEndpoint entity) {
        return new ServiceEndpointSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ServiceEndpointResponse> toResponses(List<ServiceEndpoint> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ServiceEndpoint entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getServiceId(),
                entity.getSiteId(),
                entity.getPortId(),
                entity.getEndpointRole(),
                entity.getHandoff(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
