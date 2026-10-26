package com.uniti.cil.itta.inventory.mpls.mapper;

import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.QosPolicy;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between QosPolicy entities and DTOs (kept explicit for auditability). */
@Component
public class QosPolicyMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "policy_name", "class_count", "cir_mbps", "pir_mbps", "marking_scheme", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public QosPolicy toEntity(QosPolicyCreateRequest request) {
        QosPolicy entity = new QosPolicy();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPolicyName(request.getPolicyName());
        entity.setClassCount(request.getClassCount());
        entity.setCirMbps(request.getCirMbps());
        entity.setPirMbps(request.getPirMbps());
        entity.setMarkingScheme(request.getMarkingScheme());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(QosPolicyUpdateRequest request, QosPolicy entity) {
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
        if (request.getPolicyName() != null) {
            entity.setPolicyName(request.getPolicyName());
        }
        if (request.getClassCount() != null) {
            entity.setClassCount(request.getClassCount());
        }
        if (request.getCirMbps() != null) {
            entity.setCirMbps(request.getCirMbps());
        }
        if (request.getPirMbps() != null) {
            entity.setPirMbps(request.getPirMbps());
        }
        if (request.getMarkingScheme() != null) {
            entity.setMarkingScheme(request.getMarkingScheme());
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

    public QosPolicyResponse toResponse(QosPolicy entity) {
        QosPolicyResponse response = new QosPolicyResponse();
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
        response.setPolicyName(entity.getPolicyName());
        response.setClassCount(entity.getClassCount());
        response.setCirMbps(entity.getCirMbps());
        response.setPirMbps(entity.getPirMbps());
        response.setMarkingScheme(entity.getMarkingScheme());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public QosPolicySummary toSummary(QosPolicy entity) {
        return new QosPolicySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<QosPolicyResponse> toResponses(List<QosPolicy> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(QosPolicy entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPolicyName(),
                entity.getClassCount(),
                entity.getCirMbps(),
                entity.getPirMbps(),
                entity.getMarkingScheme(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
