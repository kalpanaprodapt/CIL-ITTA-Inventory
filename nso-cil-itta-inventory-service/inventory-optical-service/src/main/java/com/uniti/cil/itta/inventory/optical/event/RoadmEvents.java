package com.uniti.cil.itta.inventory.optical.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.optical.dto.RoadmResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Roadm domain events published to the integration bus. */
public final class RoadmEvents {

    public static final String ENTITY_TYPE = "Roadm";
    public static final String CREATED = "RoadmCreated";
    public static final String UPDATED = "RoadmUpdated";
    public static final String DELETED = "RoadmDeleted";
    public static final String STATUS_CHANGED = "RoadmStatusChanged";

    private RoadmEvents() {
    }

    public static DomainEvent created(RoadmResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(RoadmResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(RoadmResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(RoadmResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, RoadmResponse response, String actor, Map<String, Object> extra) {
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
