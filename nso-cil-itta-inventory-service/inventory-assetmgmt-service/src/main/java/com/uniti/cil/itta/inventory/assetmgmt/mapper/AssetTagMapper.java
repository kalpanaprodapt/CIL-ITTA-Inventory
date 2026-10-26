package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.AssetTag;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between AssetTag entities and DTOs (kept explicit for auditability). */
@Component
public class AssetTagMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "asset_id", "tag_value", "tag_type", "printed_on", "source_system", "region", "owner", "criticality", "cost_center");

    public AssetTag toEntity(AssetTagCreateRequest request) {
        AssetTag entity = new AssetTag();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAssetId(request.getAssetId());
        entity.setTagValue(request.getTagValue());
        entity.setTagType(request.getTagType());
        entity.setPrintedOn(request.getPrintedOn());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(AssetTagUpdateRequest request, AssetTag entity) {
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
        if (request.getAssetId() != null) {
            entity.setAssetId(request.getAssetId());
        }
        if (request.getTagValue() != null) {
            entity.setTagValue(request.getTagValue());
        }
        if (request.getTagType() != null) {
            entity.setTagType(request.getTagType());
        }
        if (request.getPrintedOn() != null) {
            entity.setPrintedOn(request.getPrintedOn());
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

    public AssetTagResponse toResponse(AssetTag entity) {
        AssetTagResponse response = new AssetTagResponse();
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
        response.setAssetId(entity.getAssetId());
        response.setTagValue(entity.getTagValue());
        response.setTagType(entity.getTagType());
        response.setPrintedOn(entity.getPrintedOn());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public AssetTagSummary toSummary(AssetTag entity) {
        return new AssetTagSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AssetTagResponse> toResponses(List<AssetTag> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(AssetTag entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAssetId(),
                entity.getTagValue(),
                entity.getTagType(),
                entity.getPrintedOn(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
