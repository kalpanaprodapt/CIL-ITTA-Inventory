package com.uniti.cil.itta.inventory.switching.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.switching.dto.OspfAreaResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for OspfArea domain events published to the integration bus. */
public final class OspfAreaEvents {

    public static final String ENTITY_TYPE = "OspfArea";
    public static final String CREATED = "OspfAreaCreated";
    public static final String UPDATED = "OspfAreaUpdated";
    public static final String DELETED = "OspfAreaDeleted";
    public static final String STATUS_CHANGED = "OspfAreaStatusChanged";

    private OspfAreaEvents() {
    }

    public static DomainEvent created(OspfAreaResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(OspfAreaResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(OspfAreaResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(OspfAreaResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, OspfAreaResponse response, String actor, Map<String, Object> extra) {
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
