package com.uniti.cil.itta.inventory.alarm.mapper;

import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.EventSource;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between EventSource entities and DTOs (kept explicit for auditability). */
@Component
public class EventSourceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "source_name", "protocol", "endpoint", "heartbeat_seconds", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public EventSource toEntity(EventSourceCreateRequest request) {
        EventSource entity = new EventSource();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSourceName(request.getSourceName());
        entity.setProtocol(request.getProtocol());
        entity.setEndpoint(request.getEndpoint());
        entity.setHeartbeatSeconds(request.getHeartbeatSeconds());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(EventSourceUpdateRequest request, EventSource entity) {
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
        if (request.getSourceName() != null) {
            entity.setSourceName(request.getSourceName());
        }
        if (request.getProtocol() != null) {
            entity.setProtocol(request.getProtocol());
        }
        if (request.getEndpoint() != null) {
            entity.setEndpoint(request.getEndpoint());
        }
        if (request.getHeartbeatSeconds() != null) {
            entity.setHeartbeatSeconds(request.getHeartbeatSeconds());
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

    public EventSourceResponse toResponse(EventSource entity) {
        EventSourceResponse response = new EventSourceResponse();
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
        response.setSourceName(entity.getSourceName());
        response.setProtocol(entity.getProtocol());
        response.setEndpoint(entity.getEndpoint());
        response.setHeartbeatSeconds(entity.getHeartbeatSeconds());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public EventSourceSummary toSummary(EventSource entity) {
        return new EventSourceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<EventSourceResponse> toResponses(List<EventSource> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(EventSource entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSourceName(),
                entity.getProtocol(),
                entity.getEndpoint(),
                entity.getHeartbeatSeconds(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
