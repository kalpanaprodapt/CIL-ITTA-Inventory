package com.uniti.cil.itta.inventory.switching.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.switching.dto.BgpSessionResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for BgpSession domain events published to the integration bus. */
public final class BgpSessionEvents {

    public static final String ENTITY_TYPE = "BgpSession";
    public static final String CREATED = "BgpSessionCreated";
    public static final String UPDATED = "BgpSessionUpdated";
    public static final String DELETED = "BgpSessionDeleted";
    public static final String STATUS_CHANGED = "BgpSessionStatusChanged";

    private BgpSessionEvents() {
    }

    public static DomainEvent created(BgpSessionResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(BgpSessionResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(BgpSessionResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(BgpSessionResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, BgpSessionResponse response, String actor, Map<String, Object> extra) {
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
