package com.uniti.cil.itta.inventory.port.mapper;

import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.LagGroup;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between LagGroup entities and DTOs (kept explicit for auditability). */
@Component
public class LagGroupMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "device_id", "lag_name", "member_count", "lacp_mode", "min_links", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public LagGroup toEntity(LagGroupCreateRequest request) {
        LagGroup entity = new LagGroup();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setDeviceId(request.getDeviceId());
        entity.setLagName(request.getLagName());
        entity.setMemberCount(request.getMemberCount());
        entity.setLacpMode(request.getLacpMode());
        entity.setMinLinks(request.getMinLinks());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(LagGroupUpdateRequest request, LagGroup entity) {
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
        if (request.getDeviceId() != null) {
            entity.setDeviceId(request.getDeviceId());
        }
        if (request.getLagName() != null) {
            entity.setLagName(request.getLagName());
        }
        if (request.getMemberCount() != null) {
            entity.setMemberCount(request.getMemberCount());
        }
        if (request.getLacpMode() != null) {
            entity.setLacpMode(request.getLacpMode());
        }
        if (request.getMinLinks() != null) {
            entity.setMinLinks(request.getMinLinks());
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

    public LagGroupResponse toResponse(LagGroup entity) {
        LagGroupResponse response = new LagGroupResponse();
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
        response.setDeviceId(entity.getDeviceId());
        response.setLagName(entity.getLagName());
        response.setMemberCount(entity.getMemberCount());
        response.setLacpMode(entity.getLacpMode());
        response.setMinLinks(entity.getMinLinks());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public LagGroupSummary toSummary(LagGroup entity) {
        return new LagGroupSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<LagGroupResponse> toResponses(List<LagGroup> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(LagGroup entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getDeviceId(),
                entity.getLagName(),
                entity.getMemberCount(),
                entity.getLacpMode(),
                entity.getMinLinks(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
