package com.uniti.cil.itta.inventory.virtual.mapper;

import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.K8sCluster;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between K8sCluster entities and DTOs (kept explicit for auditability). */
@Component
public class K8sClusterMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "cluster_name", "cluster_version", "node_count", "cloud_account_id", "region", "api_endpoint", "effective_to", "labels", "lifecycle_stage", "source_system", "owner");

    public K8sCluster toEntity(K8sClusterCreateRequest request) {
        K8sCluster entity = new K8sCluster();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setClusterName(request.getClusterName());
        entity.setClusterVersion(request.getClusterVersion());
        entity.setNodeCount(request.getNodeCount());
        entity.setCloudAccountId(request.getCloudAccountId());
        entity.setRegion(request.getRegion());
        entity.setApiEndpoint(request.getApiEndpoint());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(K8sClusterUpdateRequest request, K8sCluster entity) {
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
        if (request.getClusterName() != null) {
            entity.setClusterName(request.getClusterName());
        }
        if (request.getClusterVersion() != null) {
            entity.setClusterVersion(request.getClusterVersion());
        }
        if (request.getNodeCount() != null) {
            entity.setNodeCount(request.getNodeCount());
        }
        if (request.getCloudAccountId() != null) {
            entity.setCloudAccountId(request.getCloudAccountId());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
        if (request.getApiEndpoint() != null) {
            entity.setApiEndpoint(request.getApiEndpoint());
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
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
    }

    public K8sClusterResponse toResponse(K8sCluster entity) {
        K8sClusterResponse response = new K8sClusterResponse();
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
        response.setClusterName(entity.getClusterName());
        response.setClusterVersion(entity.getClusterVersion());
        response.setNodeCount(entity.getNodeCount());
        response.setCloudAccountId(entity.getCloudAccountId());
        response.setRegion(entity.getRegion());
        response.setApiEndpoint(entity.getApiEndpoint());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setOwner(entity.getOwner());
        return response;
    }

    public K8sClusterSummary toSummary(K8sCluster entity) {
        return new K8sClusterSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<K8sClusterResponse> toResponses(List<K8sCluster> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(K8sCluster entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getClusterName(),
                entity.getClusterVersion(),
                entity.getNodeCount(),
                entity.getCloudAccountId(),
                entity.getRegion(),
                entity.getApiEndpoint(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getOwner());
    }
}
