package com.uniti.cil.itta.inventory.ipam.mapper;

import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.IpPool;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between IpPool entities and DTOs (kept explicit for auditability). */
@Component
public class IpPoolMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "subnet_id", "range_start", "range_end", "pool_type", "lease_seconds", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public IpPool toEntity(IpPoolCreateRequest request) {
        IpPool entity = new IpPool();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSubnetId(request.getSubnetId());
        entity.setRangeStart(request.getRangeStart());
        entity.setRangeEnd(request.getRangeEnd());
        entity.setPoolType(request.getPoolType());
        entity.setLeaseSeconds(request.getLeaseSeconds());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(IpPoolUpdateRequest request, IpPool entity) {
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
        if (request.getSubnetId() != null) {
            entity.setSubnetId(request.getSubnetId());
        }
        if (request.getRangeStart() != null) {
            entity.setRangeStart(request.getRangeStart());
        }
        if (request.getRangeEnd() != null) {
            entity.setRangeEnd(request.getRangeEnd());
        }
        if (request.getPoolType() != null) {
            entity.setPoolType(request.getPoolType());
        }
        if (request.getLeaseSeconds() != null) {
            entity.setLeaseSeconds(request.getLeaseSeconds());
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

    public IpPoolResponse toResponse(IpPool entity) {
        IpPoolResponse response = new IpPoolResponse();
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
        response.setSubnetId(entity.getSubnetId());
        response.setRangeStart(entity.getRangeStart());
        response.setRangeEnd(entity.getRangeEnd());
        response.setPoolType(entity.getPoolType());
        response.setLeaseSeconds(entity.getLeaseSeconds());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public IpPoolSummary toSummary(IpPool entity) {
        return new IpPoolSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<IpPoolResponse> toResponses(List<IpPool> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(IpPool entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSubnetId(),
                entity.getRangeStart(),
                entity.getRangeEnd(),
                entity.getPoolType(),
                entity.getLeaseSeconds(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
