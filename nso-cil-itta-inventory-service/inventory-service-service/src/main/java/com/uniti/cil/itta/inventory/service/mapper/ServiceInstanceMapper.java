package com.uniti.cil.itta.inventory.service.mapper;

import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.ServiceInstance;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ServiceInstance entities and DTOs (kept explicit for auditability). */
@Component
public class ServiceInstanceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "catalog_item_id", "customer_id", "activated_on", "bandwidth_mbps", "service_state", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public ServiceInstance toEntity(ServiceInstanceCreateRequest request) {
        ServiceInstance entity = new ServiceInstance();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCatalogItemId(request.getCatalogItemId());
        entity.setCustomerId(request.getCustomerId());
        entity.setActivatedOn(request.getActivatedOn());
        entity.setBandwidthMbps(request.getBandwidthMbps());
        entity.setServiceState(request.getServiceState());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(ServiceInstanceUpdateRequest request, ServiceInstance entity) {
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
        if (request.getCatalogItemId() != null) {
            entity.setCatalogItemId(request.getCatalogItemId());
        }
        if (request.getCustomerId() != null) {
            entity.setCustomerId(request.getCustomerId());
        }
        if (request.getActivatedOn() != null) {
            entity.setActivatedOn(request.getActivatedOn());
        }
        if (request.getBandwidthMbps() != null) {
            entity.setBandwidthMbps(request.getBandwidthMbps());
        }
        if (request.getServiceState() != null) {
            entity.setServiceState(request.getServiceState());
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

    public ServiceInstanceResponse toResponse(ServiceInstance entity) {
        ServiceInstanceResponse response = new ServiceInstanceResponse();
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
        response.setCatalogItemId(entity.getCatalogItemId());
        response.setCustomerId(entity.getCustomerId());
        response.setActivatedOn(entity.getActivatedOn());
        response.setBandwidthMbps(entity.getBandwidthMbps());
        response.setServiceState(entity.getServiceState());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public ServiceInstanceSummary toSummary(ServiceInstance entity) {
        return new ServiceInstanceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ServiceInstanceResponse> toResponses(List<ServiceInstance> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ServiceInstance entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCatalogItemId(),
                entity.getCustomerId(),
                entity.getActivatedOn(),
                entity.getBandwidthMbps(),
                entity.getServiceState(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
