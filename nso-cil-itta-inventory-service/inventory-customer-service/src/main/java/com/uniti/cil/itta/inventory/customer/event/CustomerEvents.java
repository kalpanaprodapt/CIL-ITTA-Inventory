package com.uniti.cil.itta.inventory.customer.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.customer.dto.CustomerResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Customer domain events published to the integration bus. */
public final class CustomerEvents {

    public static final String ENTITY_TYPE = "Customer";
    public static final String CREATED = "CustomerCreated";
    public static final String UPDATED = "CustomerUpdated";
    public static final String DELETED = "CustomerDeleted";
    public static final String STATUS_CHANGED = "CustomerStatusChanged";

    private CustomerEvents() {
    }

    public static DomainEvent created(CustomerResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CustomerResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CustomerResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CustomerResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CustomerResponse response, String actor, Map<String, Object> extra) {
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
