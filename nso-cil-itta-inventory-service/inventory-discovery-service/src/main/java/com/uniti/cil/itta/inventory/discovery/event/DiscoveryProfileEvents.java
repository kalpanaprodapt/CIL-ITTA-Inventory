package com.uniti.cil.itta.inventory.discovery.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryProfileResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for DiscoveryProfile domain events published to the integration bus. */
public final class DiscoveryProfileEvents {

    public static final String ENTITY_TYPE = "DiscoveryProfile";
    public static final String CREATED = "DiscoveryProfileCreated";
    public static final String UPDATED = "DiscoveryProfileUpdated";
    public static final String DELETED = "DiscoveryProfileDeleted";
    public static final String STATUS_CHANGED = "DiscoveryProfileStatusChanged";

    private DiscoveryProfileEvents() {
    }

    public static DomainEvent created(DiscoveryProfileResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(DiscoveryProfileResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(DiscoveryProfileResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(DiscoveryProfileResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, DiscoveryProfileResponse response, String actor, Map<String, Object> extra) {
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
