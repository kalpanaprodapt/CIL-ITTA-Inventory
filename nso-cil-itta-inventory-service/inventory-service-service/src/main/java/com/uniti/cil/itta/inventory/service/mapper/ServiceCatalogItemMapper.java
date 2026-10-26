package com.uniti.cil.itta.inventory.service.mapper;

import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.ServiceCatalogItem;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ServiceCatalogItem entities and DTOs (kept explicit for auditability). */
@Component
public class ServiceCatalogItemMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "item_name", "family", "base_price", "billing_unit", "orderable", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public ServiceCatalogItem toEntity(ServiceCatalogItemCreateRequest request) {
        ServiceCatalogItem entity = new ServiceCatalogItem();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setItemName(request.getItemName());
        entity.setFamily(request.getFamily());
        entity.setBasePrice(request.getBasePrice());
        entity.setBillingUnit(request.getBillingUnit());
        entity.setOrderable(request.getOrderable());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(ServiceCatalogItemUpdateRequest request, ServiceCatalogItem entity) {
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
        if (request.getItemName() != null) {
            entity.setItemName(request.getItemName());
        }
        if (request.getFamily() != null) {
            entity.setFamily(request.getFamily());
        }
        if (request.getBasePrice() != null) {
            entity.setBasePrice(request.getBasePrice());
        }
        if (request.getBillingUnit() != null) {
            entity.setBillingUnit(request.getBillingUnit());
        }
        if (request.getOrderable() != null) {
            entity.setOrderable(request.getOrderable());
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
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
    }

    public ServiceCatalogItemResponse toResponse(ServiceCatalogItem entity) {
        ServiceCatalogItemResponse response = new ServiceCatalogItemResponse();
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
        response.setItemName(entity.getItemName());
        response.setFamily(entity.getFamily());
        response.setBasePrice(entity.getBasePrice());
        response.setBillingUnit(entity.getBillingUnit());
        response.setOrderable(entity.getOrderable());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public ServiceCatalogItemSummary toSummary(ServiceCatalogItem entity) {
        return new ServiceCatalogItemSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ServiceCatalogItemResponse> toResponses(List<ServiceCatalogItem> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ServiceCatalogItem entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getItemName(),
                entity.getFamily(),
                entity.getBasePrice(),
                entity.getBillingUnit(),
                entity.getOrderable(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
