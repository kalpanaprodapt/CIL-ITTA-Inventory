package com.uniti.cil.itta.inventory.capacity.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.capacity.dto.ThresholdPolicyResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ThresholdPolicy domain events published to the integration bus. */
public final class ThresholdPolicyEvents {

    public static final String ENTITY_TYPE = "ThresholdPolicy";
    public static final String CREATED = "ThresholdPolicyCreated";
    public static final String UPDATED = "ThresholdPolicyUpdated";
    public static final String DELETED = "ThresholdPolicyDeleted";
    public static final String STATUS_CHANGED = "ThresholdPolicyStatusChanged";

    private ThresholdPolicyEvents() {
    }

    public static DomainEvent created(ThresholdPolicyResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ThresholdPolicyResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ThresholdPolicyResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ThresholdPolicyResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ThresholdPolicyResponse response, String actor, Map<String, Object> extra) {
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
