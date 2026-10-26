package com.uniti.cil.itta.inventory.service.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.service.dto.ServiceInstanceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ServiceInstance domain events published to the integration bus. */
public final class ServiceInstanceEvents {

    public static final String ENTITY_TYPE = "ServiceInstance";
    public static final String CREATED = "ServiceInstanceCreated";
    public static final String UPDATED = "ServiceInstanceUpdated";
    public static final String DELETED = "ServiceInstanceDeleted";
    public static final String STATUS_CHANGED = "ServiceInstanceStatusChanged";

    private ServiceInstanceEvents() {
    }

    public static DomainEvent created(ServiceInstanceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ServiceInstanceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ServiceInstanceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ServiceInstanceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ServiceInstanceResponse response, String actor, Map<String, Object> extra) {
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
