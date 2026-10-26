package com.uniti.cil.itta.inventory.integration.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.integration.dto.IntegrationEndpointResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for IntegrationEndpoint domain events published to the integration bus. */
public final class IntegrationEndpointEvents {

    public static final String ENTITY_TYPE = "IntegrationEndpoint";
    public static final String CREATED = "IntegrationEndpointCreated";
    public static final String UPDATED = "IntegrationEndpointUpdated";
    public static final String DELETED = "IntegrationEndpointDeleted";
    public static final String STATUS_CHANGED = "IntegrationEndpointStatusChanged";

    private IntegrationEndpointEvents() {
    }

    public static DomainEvent created(IntegrationEndpointResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(IntegrationEndpointResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(IntegrationEndpointResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(IntegrationEndpointResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, IntegrationEndpointResponse response, String actor, Map<String, Object> extra) {
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
