package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.Asset;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Asset entities and DTOs (kept explicit for auditability). */
@Component
public class AssetMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "asset_tag", "asset_class", "acquired_on", "purchase_price", "current_value", "custodian", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public Asset toEntity(AssetCreateRequest request) {
        Asset entity = new Asset();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAssetTag(request.getAssetTag());
        entity.setAssetClass(request.getAssetClass());
        entity.setAcquiredOn(request.getAcquiredOn());
        entity.setPurchasePrice(request.getPurchasePrice());
        entity.setCurrentValue(request.getCurrentValue());
        entity.setCustodian(request.getCustodian());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(AssetUpdateRequest request, Asset entity) {
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
        if (request.getAssetTag() != null) {
            entity.setAssetTag(request.getAssetTag());
        }
        if (request.getAssetClass() != null) {
            entity.setAssetClass(request.getAssetClass());
        }
        if (request.getAcquiredOn() != null) {
            entity.setAcquiredOn(request.getAcquiredOn());
        }
        if (request.getPurchasePrice() != null) {
            entity.setPurchasePrice(request.getPurchasePrice());
        }
        if (request.getCurrentValue() != null) {
            entity.setCurrentValue(request.getCurrentValue());
        }
        if (request.getCustodian() != null) {
            entity.setCustodian(request.getCustodian());
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
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
    }

    public AssetResponse toResponse(Asset entity) {
        AssetResponse response = new AssetResponse();
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
        response.setAssetTag(entity.getAssetTag());
        response.setAssetClass(entity.getAssetClass());
        response.setAcquiredOn(entity.getAcquiredOn());
        response.setPurchasePrice(entity.getPurchasePrice());
        response.setCurrentValue(entity.getCurrentValue());
        response.setCustodian(entity.getCustodian());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public AssetSummary toSummary(Asset entity) {
        return new AssetSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AssetResponse> toResponses(List<Asset> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Asset entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAssetTag(),
                entity.getAssetClass(),
                entity.getAcquiredOn(),
                entity.getPurchasePrice(),
                entity.getCurrentValue(),
                entity.getCustodian(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
