package com.uniti.cil.itta.inventory.service.mapper;

import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.ServiceDependency;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ServiceDependency entities and DTOs (kept explicit for auditability). */
@Component
public class ServiceDependencyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "service_id", "depends_on_id", "dependency_type", "impact_score", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public ServiceDependency toEntity(ServiceDependencyCreateRequest request) {
        ServiceDependency entity = new ServiceDependency();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setServiceId(request.getServiceId());
        entity.setDependsOnId(request.getDependsOnId());
        entity.setDependencyType(request.getDependencyType());
        entity.setImpactScore(request.getImpactScore());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(ServiceDependencyUpdateRequest request, ServiceDependency entity) {
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
        if (request.getDependsOnId() != null) {
            entity.setDependsOnId(request.getDependsOnId());
        }
        if (request.getDependencyType() != null) {
            entity.setDependencyType(request.getDependencyType());
        }
        if (request.getImpactScore() != null) {
            entity.setImpactScore(request.getImpactScore());
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
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
    }

    public ServiceDependencyResponse toResponse(ServiceDependency entity) {
        ServiceDependencyResponse response = new ServiceDependencyResponse();
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
        response.setDependsOnId(entity.getDependsOnId());
        response.setDependencyType(entity.getDependencyType());
        response.setImpactScore(entity.getImpactScore());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public ServiceDependencySummary toSummary(ServiceDependency entity) {
        return new ServiceDependencySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ServiceDependencyResponse> toResponses(List<ServiceDependency> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ServiceDependency entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getServiceId(),
                entity.getDependsOnId(),
                entity.getDependencyType(),
                entity.getImpactScore(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
