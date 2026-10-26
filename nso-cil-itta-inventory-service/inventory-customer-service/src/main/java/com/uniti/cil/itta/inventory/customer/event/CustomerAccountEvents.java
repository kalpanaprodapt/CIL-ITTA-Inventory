package com.uniti.cil.itta.inventory.customer.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.customer.dto.CustomerAccountResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for CustomerAccount domain events published to the integration bus. */
public final class CustomerAccountEvents {

    public static final String ENTITY_TYPE = "CustomerAccount";
    public static final String CREATED = "CustomerAccountCreated";
    public static final String UPDATED = "CustomerAccountUpdated";
    public static final String DELETED = "CustomerAccountDeleted";
    public static final String STATUS_CHANGED = "CustomerAccountStatusChanged";

    private CustomerAccountEvents() {
    }

    public static DomainEvent created(CustomerAccountResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CustomerAccountResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CustomerAccountResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CustomerAccountResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CustomerAccountResponse response, String actor, Map<String, Object> extra) {
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
