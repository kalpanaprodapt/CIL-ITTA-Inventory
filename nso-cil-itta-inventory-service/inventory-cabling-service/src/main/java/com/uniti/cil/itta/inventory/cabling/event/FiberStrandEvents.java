package com.uniti.cil.itta.inventory.cabling.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.cabling.dto.FiberStrandResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for FiberStrand domain events published to the integration bus. */
public final class FiberStrandEvents {

    public static final String ENTITY_TYPE = "FiberStrand";
    public static final String CREATED = "FiberStrandCreated";
    public static final String UPDATED = "FiberStrandUpdated";
    public static final String DELETED = "FiberStrandDeleted";
    public static final String STATUS_CHANGED = "FiberStrandStatusChanged";

    private FiberStrandEvents() {
    }

    public static DomainEvent created(FiberStrandResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(FiberStrandResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(FiberStrandResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(FiberStrandResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, FiberStrandResponse response, String actor, Map<String, Object> extra) {
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
