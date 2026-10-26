package com.uniti.cil.itta.inventory.circuit.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Circuit domain events published to the integration bus. */
public final class CircuitEvents {

    public static final String ENTITY_TYPE = "Circuit";
    public static final String CREATED = "CircuitCreated";
    public static final String UPDATED = "CircuitUpdated";
    public static final String DELETED = "CircuitDeleted";
    public static final String STATUS_CHANGED = "CircuitStatusChanged";

    private CircuitEvents() {
    }

    public static DomainEvent created(CircuitResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CircuitResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CircuitResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CircuitResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CircuitResponse response, String actor, Map<String, Object> extra) {
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
