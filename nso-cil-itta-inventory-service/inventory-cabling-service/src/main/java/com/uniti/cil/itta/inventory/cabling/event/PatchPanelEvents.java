package com.uniti.cil.itta.inventory.cabling.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.cabling.dto.PatchPanelResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PatchPanel domain events published to the integration bus. */
public final class PatchPanelEvents {

    public static final String ENTITY_TYPE = "PatchPanel";
    public static final String CREATED = "PatchPanelCreated";
    public static final String UPDATED = "PatchPanelUpdated";
    public static final String DELETED = "PatchPanelDeleted";
    public static final String STATUS_CHANGED = "PatchPanelStatusChanged";

    private PatchPanelEvents() {
    }

    public static DomainEvent created(PatchPanelResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PatchPanelResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PatchPanelResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PatchPanelResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PatchPanelResponse response, String actor, Map<String, Object> extra) {
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
