package com.uniti.cil.itta.inventory.switching.mapper;

import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.VlanPool;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between VlanPool entities and DTOs (kept explicit for auditability). */
@Component
public class VlanPoolMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "pool_name", "range_start", "range_end", "allocated", "scope", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public VlanPool toEntity(VlanPoolCreateRequest request) {
        VlanPool entity = new VlanPool();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPoolName(request.getPoolName());
        entity.setRangeStart(request.getRangeStart());
        entity.setRangeEnd(request.getRangeEnd());
        entity.setAllocated(request.getAllocated());
        entity.setScope(request.getScope());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(VlanPoolUpdateRequest request, VlanPool entity) {
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
        if (request.getPoolName() != null) {
            entity.setPoolName(request.getPoolName());
        }
        if (request.getRangeStart() != null) {
            entity.setRangeStart(request.getRangeStart());
        }
        if (request.getRangeEnd() != null) {
            entity.setRangeEnd(request.getRangeEnd());
        }
        if (request.getAllocated() != null) {
            entity.setAllocated(request.getAllocated());
        }
        if (request.getScope() != null) {
            entity.setScope(request.getScope());
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

    public VlanPoolResponse toResponse(VlanPool entity) {
        VlanPoolResponse response = new VlanPoolResponse();
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
        response.setPoolName(entity.getPoolName());
        response.setRangeStart(entity.getRangeStart());
        response.setRangeEnd(entity.getRangeEnd());
        response.setAllocated(entity.getAllocated());
        response.setScope(entity.getScope());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public VlanPoolSummary toSummary(VlanPool entity) {
        return new VlanPoolSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VlanPoolResponse> toResponses(List<VlanPool> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(VlanPool entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPoolName(),
                entity.getRangeStart(),
                entity.getRangeEnd(),
                entity.getAllocated(),
                entity.getScope(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
