package com.uniti.cil.itta.inventory.virtual.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.virtual.dto.VnfResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Vnf domain events published to the integration bus. */
public final class VnfEvents {

    public static final String ENTITY_TYPE = "Vnf";
    public static final String CREATED = "VnfCreated";
    public static final String UPDATED = "VnfUpdated";
    public static final String DELETED = "VnfDeleted";
    public static final String STATUS_CHANGED = "VnfStatusChanged";

    private VnfEvents() {
    }

    public static DomainEvent created(VnfResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(VnfResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(VnfResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(VnfResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, VnfResponse response, String actor, Map<String, Object> extra) {
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
