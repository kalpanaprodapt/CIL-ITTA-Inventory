package com.uniti.cil.itta.inventory.discovery.mapper;

import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.DiscoveredAsset;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between DiscoveredAsset entities and DTOs (kept explicit for auditability). */
@Component
public class DiscoveredAssetMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "job_id", "asset_type", "fingerprint", "raw_payload", "matched_asset_id", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public DiscoveredAsset toEntity(DiscoveredAssetCreateRequest request) {
        DiscoveredAsset entity = new DiscoveredAsset();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setJobId(request.getJobId());
        entity.setAssetType(request.getAssetType());
        entity.setFingerprint(request.getFingerprint());
        entity.setRawPayload(request.getRawPayload());
        entity.setMatchedAssetId(request.getMatchedAssetId());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(DiscoveredAssetUpdateRequest request, DiscoveredAsset entity) {
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
        if (request.getJobId() != null) {
            entity.setJobId(request.getJobId());
        }
        if (request.getAssetType() != null) {
            entity.setAssetType(request.getAssetType());
        }
        if (request.getFingerprint() != null) {
            entity.setFingerprint(request.getFingerprint());
        }
        if (request.getRawPayload() != null) {
            entity.setRawPayload(request.getRawPayload());
        }
        if (request.getMatchedAssetId() != null) {
            entity.setMatchedAssetId(request.getMatchedAssetId());
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
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public DiscoveredAssetResponse toResponse(DiscoveredAsset entity) {
        DiscoveredAssetResponse response = new DiscoveredAssetResponse();
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
        response.setJobId(entity.getJobId());
        response.setAssetType(entity.getAssetType());
        response.setFingerprint(entity.getFingerprint());
        response.setRawPayload(entity.getRawPayload());
        response.setMatchedAssetId(entity.getMatchedAssetId());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public DiscoveredAssetSummary toSummary(DiscoveredAsset entity) {
        return new DiscoveredAssetSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<DiscoveredAssetResponse> toResponses(List<DiscoveredAsset> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(DiscoveredAsset entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getJobId(),
                entity.getAssetType(),
                entity.getFingerprint(),
                entity.getRawPayload(),
                entity.getMatchedAssetId(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
