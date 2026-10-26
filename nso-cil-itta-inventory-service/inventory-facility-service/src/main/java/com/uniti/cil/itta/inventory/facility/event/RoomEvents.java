package com.uniti.cil.itta.inventory.facility.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.facility.dto.RoomResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Room domain events published to the integration bus. */
public final class RoomEvents {

    public static final String ENTITY_TYPE = "Room";
    public static final String CREATED = "RoomCreated";
    public static final String UPDATED = "RoomUpdated";
    public static final String DELETED = "RoomDeleted";
    public static final String STATUS_CHANGED = "RoomStatusChanged";

    private RoomEvents() {
    }

    public static DomainEvent created(RoomResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(RoomResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(RoomResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(RoomResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, RoomResponse response, String actor, Map<String, Object> extra) {
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
