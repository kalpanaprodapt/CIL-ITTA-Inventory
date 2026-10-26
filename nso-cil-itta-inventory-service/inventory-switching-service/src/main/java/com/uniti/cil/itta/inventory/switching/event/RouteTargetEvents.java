package com.uniti.cil.itta.inventory.switching.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.switching.dto.RouteTargetResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for RouteTarget domain events published to the integration bus. */
public final class RouteTargetEvents {

    public static final String ENTITY_TYPE = "RouteTarget";
    public static final String CREATED = "RouteTargetCreated";
    public static final String UPDATED = "RouteTargetUpdated";
    public static final String DELETED = "RouteTargetDeleted";
    public static final String STATUS_CHANGED = "RouteTargetStatusChanged";

    private RouteTargetEvents() {
    }

    public static DomainEvent created(RouteTargetResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(RouteTargetResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(RouteTargetResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(RouteTargetResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, RouteTargetResponse response, String actor, Map<String, Object> extra) {
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
