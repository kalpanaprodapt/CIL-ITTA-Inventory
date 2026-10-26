package com.uniti.cil.itta.inventory.facility.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.facility.dto.RackResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Rack domain events published to the integration bus. */
public final class RackEvents {

    public static final String ENTITY_TYPE = "Rack";
    public static final String CREATED = "RackCreated";
    public static final String UPDATED = "RackUpdated";
    public static final String DELETED = "RackDeleted";
    public static final String STATUS_CHANGED = "RackStatusChanged";

    private RackEvents() {
    }

    public static DomainEvent created(RackResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(RackResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(RackResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(RackResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, RackResponse response, String actor, Map<String, Object> extra) {
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
