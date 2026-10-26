package com.uniti.cil.itta.inventory.cabling.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.cabling.dto.PatchPortResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PatchPort domain events published to the integration bus. */
public final class PatchPortEvents {

    public static final String ENTITY_TYPE = "PatchPort";
    public static final String CREATED = "PatchPortCreated";
    public static final String UPDATED = "PatchPortUpdated";
    public static final String DELETED = "PatchPortDeleted";
    public static final String STATUS_CHANGED = "PatchPortStatusChanged";

    private PatchPortEvents() {
    }

    public static DomainEvent created(PatchPortResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PatchPortResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PatchPortResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PatchPortResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PatchPortResponse response, String actor, Map<String, Object> extra) {
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
