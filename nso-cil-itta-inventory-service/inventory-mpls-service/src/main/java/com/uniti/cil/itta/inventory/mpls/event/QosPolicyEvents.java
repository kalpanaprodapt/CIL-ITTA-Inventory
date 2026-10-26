package com.uniti.cil.itta.inventory.mpls.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.mpls.dto.QosPolicyResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for QosPolicy domain events published to the integration bus. */
public final class QosPolicyEvents {

    public static final String ENTITY_TYPE = "QosPolicy";
    public static final String CREATED = "QosPolicyCreated";
    public static final String UPDATED = "QosPolicyUpdated";
    public static final String DELETED = "QosPolicyDeleted";
    public static final String STATUS_CHANGED = "QosPolicyStatusChanged";

    private QosPolicyEvents() {
    }

    public static DomainEvent created(QosPolicyResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(QosPolicyResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(QosPolicyResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(QosPolicyResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, QosPolicyResponse response, String actor, Map<String, Object> extra) {
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
