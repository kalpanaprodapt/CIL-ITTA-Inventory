package com.uniti.cil.itta.inventory.facility.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.facility.dto.FloorResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Floor domain events published to the integration bus. */
public final class FloorEvents {

    public static final String ENTITY_TYPE = "Floor";
    public static final String CREATED = "FloorCreated";
    public static final String UPDATED = "FloorUpdated";
    public static final String DELETED = "FloorDeleted";
    public static final String STATUS_CHANGED = "FloorStatusChanged";

    private FloorEvents() {
    }

    public static DomainEvent created(FloorResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(FloorResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(FloorResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(FloorResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, FloorResponse response, String actor, Map<String, Object> extra) {
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
