package com.uniti.cil.itta.inventory.vendor.mapper;

import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.Sku;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Sku entities and DTOs (kept explicit for auditability). */
@Component
public class SkuMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "model_id", "sku_code", "list_price", "currency", "lead_time_days", "source_system", "region", "owner", "criticality", "cost_center");

    public Sku toEntity(SkuCreateRequest request) {
        Sku entity = new Sku();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setModelId(request.getModelId());
        entity.setSkuCode(request.getSkuCode());
        entity.setListPrice(request.getListPrice());
        entity.setCurrency(request.getCurrency());
        entity.setLeadTimeDays(request.getLeadTimeDays());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(SkuUpdateRequest request, Sku entity) {
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
        if (request.getModelId() != null) {
            entity.setModelId(request.getModelId());
        }
        if (request.getSkuCode() != null) {
            entity.setSkuCode(request.getSkuCode());
        }
        if (request.getListPrice() != null) {
            entity.setListPrice(request.getListPrice());
        }
        if (request.getCurrency() != null) {
            entity.setCurrency(request.getCurrency());
        }
        if (request.getLeadTimeDays() != null) {
            entity.setLeadTimeDays(request.getLeadTimeDays());
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

    public SkuResponse toResponse(Sku entity) {
        SkuResponse response = new SkuResponse();
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
        response.setModelId(entity.getModelId());
        response.setSkuCode(entity.getSkuCode());
        response.setListPrice(entity.getListPrice());
        response.setCurrency(entity.getCurrency());
        response.setLeadTimeDays(entity.getLeadTimeDays());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public SkuSummary toSummary(Sku entity) {
        return new SkuSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SkuResponse> toResponses(List<Sku> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Sku entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getModelId(),
                entity.getSkuCode(),
                entity.getListPrice(),
                entity.getCurrency(),
                entity.getLeadTimeDays(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
