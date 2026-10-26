package com.uniti.cil.itta.inventory.radio.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.radio.dto.BackhaulLinkResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for BackhaulLink domain events published to the integration bus. */
public final class BackhaulLinkEvents {

    public static final String ENTITY_TYPE = "BackhaulLink";
    public static final String CREATED = "BackhaulLinkCreated";
    public static final String UPDATED = "BackhaulLinkUpdated";
    public static final String DELETED = "BackhaulLinkDeleted";
    public static final String STATUS_CHANGED = "BackhaulLinkStatusChanged";

    private BackhaulLinkEvents() {
    }

    public static DomainEvent created(BackhaulLinkResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(BackhaulLinkResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(BackhaulLinkResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(BackhaulLinkResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, BackhaulLinkResponse response, String actor, Map<String, Object> extra) {
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
