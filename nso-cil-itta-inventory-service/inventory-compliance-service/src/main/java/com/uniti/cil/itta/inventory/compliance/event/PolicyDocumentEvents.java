package com.uniti.cil.itta.inventory.compliance.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.compliance.dto.PolicyDocumentResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PolicyDocument domain events published to the integration bus. */
public final class PolicyDocumentEvents {

    public static final String ENTITY_TYPE = "PolicyDocument";
    public static final String CREATED = "PolicyDocumentCreated";
    public static final String UPDATED = "PolicyDocumentUpdated";
    public static final String DELETED = "PolicyDocumentDeleted";
    public static final String STATUS_CHANGED = "PolicyDocumentStatusChanged";

    private PolicyDocumentEvents() {
    }

    public static DomainEvent created(PolicyDocumentResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PolicyDocumentResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PolicyDocumentResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PolicyDocumentResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PolicyDocumentResponse response, String actor, Map<String, Object> extra) {
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
