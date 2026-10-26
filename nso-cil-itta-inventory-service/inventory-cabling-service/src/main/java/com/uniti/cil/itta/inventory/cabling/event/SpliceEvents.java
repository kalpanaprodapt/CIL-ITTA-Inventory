package com.uniti.cil.itta.inventory.cabling.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.cabling.dto.SpliceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Splice domain events published to the integration bus. */
public final class SpliceEvents {

    public static final String ENTITY_TYPE = "Splice";
    public static final String CREATED = "SpliceCreated";
    public static final String UPDATED = "SpliceUpdated";
    public static final String DELETED = "SpliceDeleted";
    public static final String STATUS_CHANGED = "SpliceStatusChanged";

    private SpliceEvents() {
    }

    public static DomainEvent created(SpliceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SpliceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SpliceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SpliceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SpliceResponse response, String actor, Map<String, Object> extra) {
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
