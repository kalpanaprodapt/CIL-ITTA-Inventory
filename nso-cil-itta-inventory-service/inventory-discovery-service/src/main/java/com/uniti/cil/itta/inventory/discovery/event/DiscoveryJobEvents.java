package com.uniti.cil.itta.inventory.discovery.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryJobResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for DiscoveryJob domain events published to the integration bus. */
public final class DiscoveryJobEvents {

    public static final String ENTITY_TYPE = "DiscoveryJob";
    public static final String CREATED = "DiscoveryJobCreated";
    public static final String UPDATED = "DiscoveryJobUpdated";
    public static final String DELETED = "DiscoveryJobDeleted";
    public static final String STATUS_CHANGED = "DiscoveryJobStatusChanged";

    private DiscoveryJobEvents() {
    }

    public static DomainEvent created(DiscoveryJobResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(DiscoveryJobResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(DiscoveryJobResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(DiscoveryJobResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, DiscoveryJobResponse response, String actor, Map<String, Object> extra) {
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
