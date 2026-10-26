package com.uniti.cil.itta.inventory.alarm.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.alarm.dto.EventSourceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for EventSource domain events published to the integration bus. */
public final class EventSourceEvents {

    public static final String ENTITY_TYPE = "EventSource";
    public static final String CREATED = "EventSourceCreated";
    public static final String UPDATED = "EventSourceUpdated";
    public static final String DELETED = "EventSourceDeleted";
    public static final String STATUS_CHANGED = "EventSourceStatusChanged";

    private EventSourceEvents() {
    }

    public static DomainEvent created(EventSourceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(EventSourceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(EventSourceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(EventSourceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, EventSourceResponse response, String actor, Map<String, Object> extra) {
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
