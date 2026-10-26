package com.uniti.cil.itta.inventory.vendor.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.vendor.dto.SkuResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Sku domain events published to the integration bus. */
public final class SkuEvents {

    public static final String ENTITY_TYPE = "Sku";
    public static final String CREATED = "SkuCreated";
    public static final String UPDATED = "SkuUpdated";
    public static final String DELETED = "SkuDeleted";
    public static final String STATUS_CHANGED = "SkuStatusChanged";

    private SkuEvents() {
    }

    public static DomainEvent created(SkuResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SkuResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SkuResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SkuResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SkuResponse response, String actor, Map<String, Object> extra) {
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
