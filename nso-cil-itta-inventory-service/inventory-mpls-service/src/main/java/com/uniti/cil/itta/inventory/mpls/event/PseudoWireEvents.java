package com.uniti.cil.itta.inventory.mpls.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.mpls.dto.PseudoWireResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PseudoWire domain events published to the integration bus. */
public final class PseudoWireEvents {

    public static final String ENTITY_TYPE = "PseudoWire";
    public static final String CREATED = "PseudoWireCreated";
    public static final String UPDATED = "PseudoWireUpdated";
    public static final String DELETED = "PseudoWireDeleted";
    public static final String STATUS_CHANGED = "PseudoWireStatusChanged";

    private PseudoWireEvents() {
    }

    public static DomainEvent created(PseudoWireResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PseudoWireResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PseudoWireResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PseudoWireResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PseudoWireResponse response, String actor, Map<String, Object> extra) {
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
