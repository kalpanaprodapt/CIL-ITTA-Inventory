package com.uniti.cil.itta.inventory.alarm.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.alarm.dto.TicketLinkResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for TicketLink domain events published to the integration bus. */
public final class TicketLinkEvents {

    public static final String ENTITY_TYPE = "TicketLink";
    public static final String CREATED = "TicketLinkCreated";
    public static final String UPDATED = "TicketLinkUpdated";
    public static final String DELETED = "TicketLinkDeleted";
    public static final String STATUS_CHANGED = "TicketLinkStatusChanged";

    private TicketLinkEvents() {
    }

    public static DomainEvent created(TicketLinkResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(TicketLinkResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(TicketLinkResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(TicketLinkResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, TicketLinkResponse response, String actor, Map<String, Object> extra) {
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
