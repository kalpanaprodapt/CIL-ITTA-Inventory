package com.uniti.cil.itta.inventory.facility.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.facility.dto.PowerFeedResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PowerFeed domain events published to the integration bus. */
public final class PowerFeedEvents {

    public static final String ENTITY_TYPE = "PowerFeed";
    public static final String CREATED = "PowerFeedCreated";
    public static final String UPDATED = "PowerFeedUpdated";
    public static final String DELETED = "PowerFeedDeleted";
    public static final String STATUS_CHANGED = "PowerFeedStatusChanged";

    private PowerFeedEvents() {
    }

    public static DomainEvent created(PowerFeedResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PowerFeedResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PowerFeedResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PowerFeedResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PowerFeedResponse response, String actor, Map<String, Object> extra) {
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
