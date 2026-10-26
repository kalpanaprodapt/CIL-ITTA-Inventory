package com.uniti.cil.itta.inventory.switching.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.switching.dto.VlanResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Vlan domain events published to the integration bus. */
public final class VlanEvents {

    public static final String ENTITY_TYPE = "Vlan";
    public static final String CREATED = "VlanCreated";
    public static final String UPDATED = "VlanUpdated";
    public static final String DELETED = "VlanDeleted";
    public static final String STATUS_CHANGED = "VlanStatusChanged";

    private VlanEvents() {
    }

    public static DomainEvent created(VlanResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(VlanResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(VlanResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(VlanResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, VlanResponse response, String actor, Map<String, Object> extra) {
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
