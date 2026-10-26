package com.uniti.cil.itta.inventory.optical.mapper;

import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.OtnLink;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between OtnLink entities and DTOs (kept explicit for auditability). */
@Component
public class OtnLinkMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "a_node_id", "z_node_id", "odu", "latency_us", "protection", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public OtnLink toEntity(OtnLinkCreateRequest request) {
        OtnLink entity = new OtnLink();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setANodeId(request.getANodeId());
        entity.setZNodeId(request.getZNodeId());
        entity.setOdu(request.getOdu());
        entity.setLatencyUs(request.getLatencyUs());
        entity.setProtection(request.getProtection());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(OtnLinkUpdateRequest request, OtnLink entity) {
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
        if (request.getANodeId() != null) {
            entity.setANodeId(request.getANodeId());
        }
        if (request.getZNodeId() != null) {
            entity.setZNodeId(request.getZNodeId());
        }
        if (request.getOdu() != null) {
            entity.setOdu(request.getOdu());
        }
        if (request.getLatencyUs() != null) {
            entity.setLatencyUs(request.getLatencyUs());
        }
        if (request.getProtection() != null) {
            entity.setProtection(request.getProtection());
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

    public OtnLinkResponse toResponse(OtnLink entity) {
        OtnLinkResponse response = new OtnLinkResponse();
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
        response.setANodeId(entity.getANodeId());
        response.setZNodeId(entity.getZNodeId());
        response.setOdu(entity.getOdu());
        response.setLatencyUs(entity.getLatencyUs());
        response.setProtection(entity.getProtection());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public OtnLinkSummary toSummary(OtnLink entity) {
        return new OtnLinkSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<OtnLinkResponse> toResponses(List<OtnLink> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(OtnLink entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getANodeId(),
                entity.getZNodeId(),
                entity.getOdu(),
                entity.getLatencyUs(),
                entity.getProtection(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
