package com.uniti.cil.itta.inventory.port.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.port.dto.PortResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Port domain events published to the integration bus. */
public final class PortEvents {

    public static final String ENTITY_TYPE = "Port";
    public static final String CREATED = "PortCreated";
    public static final String UPDATED = "PortUpdated";
    public static final String DELETED = "PortDeleted";
    public static final String STATUS_CHANGED = "PortStatusChanged";

    private PortEvents() {
    }

    public static DomainEvent created(PortResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PortResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PortResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PortResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PortResponse response, String actor, Map<String, Object> extra) {
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
