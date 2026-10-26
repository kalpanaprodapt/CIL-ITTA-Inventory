package com.uniti.cil.itta.inventory.optical.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.optical.dto.AmplifierResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Amplifier domain events published to the integration bus. */
public final class AmplifierEvents {

    public static final String ENTITY_TYPE = "Amplifier";
    public static final String CREATED = "AmplifierCreated";
    public static final String UPDATED = "AmplifierUpdated";
    public static final String DELETED = "AmplifierDeleted";
    public static final String STATUS_CHANGED = "AmplifierStatusChanged";

    private AmplifierEvents() {
    }

    public static DomainEvent created(AmplifierResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(AmplifierResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(AmplifierResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(AmplifierResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, AmplifierResponse response, String actor, Map<String, Object> extra) {
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
