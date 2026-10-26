package com.uniti.cil.itta.inventory.device.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.device.dto.NetworkDeviceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for NetworkDevice domain events published to the integration bus. */
public final class NetworkDeviceEvents {

    public static final String ENTITY_TYPE = "NetworkDevice";
    public static final String CREATED = "NetworkDeviceCreated";
    public static final String UPDATED = "NetworkDeviceUpdated";
    public static final String DELETED = "NetworkDeviceDeleted";
    public static final String STATUS_CHANGED = "NetworkDeviceStatusChanged";

    private NetworkDeviceEvents() {
    }

    public static DomainEvent created(NetworkDeviceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(NetworkDeviceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(NetworkDeviceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(NetworkDeviceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, NetworkDeviceResponse response, String actor, Map<String, Object> extra) {
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
