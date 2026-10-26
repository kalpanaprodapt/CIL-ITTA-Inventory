package com.uniti.cil.itta.inventory.topology.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.topology.dto.PathTraceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PathTrace domain events published to the integration bus. */
public final class PathTraceEvents {

    public static final String ENTITY_TYPE = "PathTrace";
    public static final String CREATED = "PathTraceCreated";
    public static final String UPDATED = "PathTraceUpdated";
    public static final String DELETED = "PathTraceDeleted";
    public static final String STATUS_CHANGED = "PathTraceStatusChanged";

    private PathTraceEvents() {
    }

    public static DomainEvent created(PathTraceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PathTraceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PathTraceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PathTraceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PathTraceResponse response, String actor, Map<String, Object> extra) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", response.getId());
        payload.put("code", response.getCode());
        payload.put("name", response.getName());
        payload.put("status", response.getStatus());
        payload.put("version", response.getVersion());
        if (extra != null) {
            payload.putAll(extra);
        }
        return new DomainEvent(type, ENTITY_TYPE, response.getId(), response.getTenantId(), actor, payload);
    }
}
