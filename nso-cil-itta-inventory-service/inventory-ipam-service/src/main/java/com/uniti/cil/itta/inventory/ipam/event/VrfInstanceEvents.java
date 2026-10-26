package com.uniti.cil.itta.inventory.ipam.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.ipam.dto.VrfInstanceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for VrfInstance domain events published to the integration bus. */
public final class VrfInstanceEvents {

    public static final String ENTITY_TYPE = "VrfInstance";
    public static final String CREATED = "VrfInstanceCreated";
    public static final String UPDATED = "VrfInstanceUpdated";
    public static final String DELETED = "VrfInstanceDeleted";
    public static final String STATUS_CHANGED = "VrfInstanceStatusChanged";

    private VrfInstanceEvents() {
    }

    public static DomainEvent created(VrfInstanceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(VrfInstanceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(VrfInstanceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(VrfInstanceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, VrfInstanceResponse response, String actor, Map<String, Object> extra) {
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
