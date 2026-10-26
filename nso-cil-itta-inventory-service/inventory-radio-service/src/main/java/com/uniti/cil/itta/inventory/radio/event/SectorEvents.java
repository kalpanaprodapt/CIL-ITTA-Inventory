package com.uniti.cil.itta.inventory.radio.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.radio.dto.SectorResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Sector domain events published to the integration bus. */
public final class SectorEvents {

    public static final String ENTITY_TYPE = "Sector";
    public static final String CREATED = "SectorCreated";
    public static final String UPDATED = "SectorUpdated";
    public static final String DELETED = "SectorDeleted";
    public static final String STATUS_CHANGED = "SectorStatusChanged";

    private SectorEvents() {
    }

    public static DomainEvent created(SectorResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SectorResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SectorResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SectorResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SectorResponse response, String actor, Map<String, Object> extra) {
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
