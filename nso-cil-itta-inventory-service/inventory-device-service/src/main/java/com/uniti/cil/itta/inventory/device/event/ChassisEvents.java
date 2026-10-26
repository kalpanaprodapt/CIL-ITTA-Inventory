package com.uniti.cil.itta.inventory.device.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.device.dto.ChassisResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Chassis domain events published to the integration bus. */
public final class ChassisEvents {

    public static final String ENTITY_TYPE = "Chassis";
    public static final String CREATED = "ChassisCreated";
    public static final String UPDATED = "ChassisUpdated";
    public static final String DELETED = "ChassisDeleted";
    public static final String STATUS_CHANGED = "ChassisStatusChanged";

    private ChassisEvents() {
    }

    public static DomainEvent created(ChassisResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ChassisResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ChassisResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ChassisResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ChassisResponse response, String actor, Map<String, Object> extra) {
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
