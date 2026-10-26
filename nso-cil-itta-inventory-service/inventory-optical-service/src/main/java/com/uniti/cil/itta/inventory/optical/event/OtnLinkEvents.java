package com.uniti.cil.itta.inventory.optical.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.optical.dto.OtnLinkResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for OtnLink domain events published to the integration bus. */
public final class OtnLinkEvents {

    public static final String ENTITY_TYPE = "OtnLink";
    public static final String CREATED = "OtnLinkCreated";
    public static final String UPDATED = "OtnLinkUpdated";
    public static final String DELETED = "OtnLinkDeleted";
    public static final String STATUS_CHANGED = "OtnLinkStatusChanged";

    private OtnLinkEvents() {
    }

    public static DomainEvent created(OtnLinkResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(OtnLinkResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(OtnLinkResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(OtnLinkResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, OtnLinkResponse response, String actor, Map<String, Object> extra) {
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
