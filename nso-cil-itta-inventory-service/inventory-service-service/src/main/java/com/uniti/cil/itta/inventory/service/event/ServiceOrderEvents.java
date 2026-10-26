package com.uniti.cil.itta.inventory.service.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.service.dto.ServiceOrderResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ServiceOrder domain events published to the integration bus. */
public final class ServiceOrderEvents {

    public static final String ENTITY_TYPE = "ServiceOrder";
    public static final String CREATED = "ServiceOrderCreated";
    public static final String UPDATED = "ServiceOrderUpdated";
    public static final String DELETED = "ServiceOrderDeleted";
    public static final String STATUS_CHANGED = "ServiceOrderStatusChanged";

    private ServiceOrderEvents() {
    }

    public static DomainEvent created(ServiceOrderResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ServiceOrderResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ServiceOrderResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ServiceOrderResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ServiceOrderResponse response, String actor, Map<String, Object> extra) {
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
