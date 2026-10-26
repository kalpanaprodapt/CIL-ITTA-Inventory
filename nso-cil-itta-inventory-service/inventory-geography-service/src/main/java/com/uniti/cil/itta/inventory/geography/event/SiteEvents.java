package com.uniti.cil.itta.inventory.geography.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.geography.dto.SiteResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Site domain events published to the integration bus. */
public final class SiteEvents {

    public static final String ENTITY_TYPE = "Site";
    public static final String CREATED = "SiteCreated";
    public static final String UPDATED = "SiteUpdated";
    public static final String DELETED = "SiteDeleted";
    public static final String STATUS_CHANGED = "SiteStatusChanged";

    private SiteEvents() {
    }

    public static DomainEvent created(SiteResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SiteResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SiteResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SiteResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SiteResponse response, String actor, Map<String, Object> extra) {
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
