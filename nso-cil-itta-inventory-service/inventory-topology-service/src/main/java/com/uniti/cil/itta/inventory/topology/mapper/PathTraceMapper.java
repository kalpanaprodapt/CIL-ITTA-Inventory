package com.uniti.cil.itta.inventory.topology.mapper;

import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.PathTrace;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PathTrace entities and DTOs (kept explicit for auditability). */
@Component
public class PathTraceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "source_ref", "target_ref", "hop_count", "total_latency_ms", "trace_method", "traced_at", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public PathTrace toEntity(PathTraceCreateRequest request) {
        PathTrace entity = new PathTrace();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSourceRef(request.getSourceRef());
        entity.setTargetRef(request.getTargetRef());
        entity.setHopCount(request.getHopCount());
        entity.setTotalLatencyMs(request.getTotalLatencyMs());
        entity.setTraceMethod(request.getTraceMethod());
        entity.setTracedAt(request.getTracedAt());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(PathTraceUpdateRequest request, PathTrace entity) {
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
        if (request.getSourceRef() != null) {
            entity.setSourceRef(request.getSourceRef());
        }
        if (request.getTargetRef() != null) {
            entity.setTargetRef(request.getTargetRef());
        }
        if (request.getHopCount() != null) {
            entity.setHopCount(request.getHopCount());
        }
        if (request.getTotalLatencyMs() != null) {
            entity.setTotalLatencyMs(request.getTotalLatencyMs());
        }
        if (request.getTraceMethod() != null) {
            entity.setTraceMethod(request.getTraceMethod());
        }
        if (request.getTracedAt() != null) {
            entity.setTracedAt(request.getTracedAt());
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
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
    }

    public PathTraceResponse toResponse(PathTrace entity) {
        PathTraceResponse response = new PathTraceResponse();
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
        response.setSourceRef(entity.getSourceRef());
        response.setTargetRef(entity.getTargetRef());
        response.setHopCount(entity.getHopCount());
        response.setTotalLatencyMs(entity.getTotalLatencyMs());
        response.setTraceMethod(entity.getTraceMethod());
        response.setTracedAt(entity.getTracedAt());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public PathTraceSummary toSummary(PathTrace entity) {
        return new PathTraceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PathTraceResponse> toResponses(List<PathTrace> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PathTrace entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSourceRef(),
                entity.getTargetRef(),
                entity.getHopCount(),
                entity.getTotalLatencyMs(),
                entity.getTraceMethod(),
                entity.getTracedAt(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
