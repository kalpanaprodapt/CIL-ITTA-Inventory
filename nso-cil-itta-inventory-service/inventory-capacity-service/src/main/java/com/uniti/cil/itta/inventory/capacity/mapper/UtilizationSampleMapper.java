package com.uniti.cil.itta.inventory.capacity.mapper;

import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.UtilizationSample;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between UtilizationSample entities and DTOs (kept explicit for auditability). */
@Component
public class UtilizationSampleMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "target_ref", "metric", "sampled_at", "value_pct", "peak", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public UtilizationSample toEntity(UtilizationSampleCreateRequest request) {
        UtilizationSample entity = new UtilizationSample();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setTargetRef(request.getTargetRef());
        entity.setMetric(request.getMetric());
        entity.setSampledAt(request.getSampledAt());
        entity.setValuePct(request.getValuePct());
        entity.setPeak(request.getPeak());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(UtilizationSampleUpdateRequest request, UtilizationSample entity) {
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
        if (request.getTargetRef() != null) {
            entity.setTargetRef(request.getTargetRef());
        }
        if (request.getMetric() != null) {
            entity.setMetric(request.getMetric());
        }
        if (request.getSampledAt() != null) {
            entity.setSampledAt(request.getSampledAt());
        }
        if (request.getValuePct() != null) {
            entity.setValuePct(request.getValuePct());
        }
        if (request.getPeak() != null) {
            entity.setPeak(request.getPeak());
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

    public UtilizationSampleResponse toResponse(UtilizationSample entity) {
        UtilizationSampleResponse response = new UtilizationSampleResponse();
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
        response.setTargetRef(entity.getTargetRef());
        response.setMetric(entity.getMetric());
        response.setSampledAt(entity.getSampledAt());
        response.setValuePct(entity.getValuePct());
        response.setPeak(entity.getPeak());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public UtilizationSampleSummary toSummary(UtilizationSample entity) {
        return new UtilizationSampleSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<UtilizationSampleResponse> toResponses(List<UtilizationSample> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(UtilizationSample entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getTargetRef(),
                entity.getMetric(),
                entity.getSampledAt(),
                entity.getValuePct(),
                entity.getPeak(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
