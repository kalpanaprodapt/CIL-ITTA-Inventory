package com.uniti.cil.itta.inventory.vendor.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.vendor.dto.WarrantyResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Warranty domain events published to the integration bus. */
public final class WarrantyEvents {

    public static final String ENTITY_TYPE = "Warranty";
    public static final String CREATED = "WarrantyCreated";
    public static final String UPDATED = "WarrantyUpdated";
    public static final String DELETED = "WarrantyDeleted";
    public static final String STATUS_CHANGED = "WarrantyStatusChanged";

    private WarrantyEvents() {
    }

    public static DomainEvent created(WarrantyResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(WarrantyResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(WarrantyResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(WarrantyResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, WarrantyResponse response, String actor, Map<String, Object> extra) {
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
