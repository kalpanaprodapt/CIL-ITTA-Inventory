package com.uniti.cil.itta.inventory.topology.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.topology.dto.LinkResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Link domain events published to the integration bus. */
public final class LinkEvents {

    public static final String ENTITY_TYPE = "Link";
    public static final String CREATED = "LinkCreated";
    public static final String UPDATED = "LinkUpdated";
    public static final String DELETED = "LinkDeleted";
    public static final String STATUS_CHANGED = "LinkStatusChanged";

    private LinkEvents() {
    }

    public static DomainEvent created(LinkResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(LinkResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(LinkResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(LinkResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, LinkResponse response, String actor, Map<String, Object> extra) {
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
