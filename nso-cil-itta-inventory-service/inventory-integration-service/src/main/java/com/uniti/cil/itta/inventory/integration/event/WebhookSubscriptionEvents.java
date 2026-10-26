package com.uniti.cil.itta.inventory.integration.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.integration.dto.WebhookSubscriptionResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for WebhookSubscription domain events published to the integration bus. */
public final class WebhookSubscriptionEvents {

    public static final String ENTITY_TYPE = "WebhookSubscription";
    public static final String CREATED = "WebhookSubscriptionCreated";
    public static final String UPDATED = "WebhookSubscriptionUpdated";
    public static final String DELETED = "WebhookSubscriptionDeleted";
    public static final String STATUS_CHANGED = "WebhookSubscriptionStatusChanged";

    private WebhookSubscriptionEvents() {
    }

    public static DomainEvent created(WebhookSubscriptionResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(WebhookSubscriptionResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(WebhookSubscriptionResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(WebhookSubscriptionResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, WebhookSubscriptionResponse response, String actor, Map<String, Object> extra) {
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
