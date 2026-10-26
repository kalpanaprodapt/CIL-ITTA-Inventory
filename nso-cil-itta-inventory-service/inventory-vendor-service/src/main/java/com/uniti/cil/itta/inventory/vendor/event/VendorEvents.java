package com.uniti.cil.itta.inventory.vendor.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.vendor.dto.VendorResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Vendor domain events published to the integration bus. */
public final class VendorEvents {

    public static final String ENTITY_TYPE = "Vendor";
    public static final String CREATED = "VendorCreated";
    public static final String UPDATED = "VendorUpdated";
    public static final String DELETED = "VendorDeleted";
    public static final String STATUS_CHANGED = "VendorStatusChanged";

    private VendorEvents() {
    }

    public static DomainEvent created(VendorResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(VendorResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(VendorResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(VendorResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, VendorResponse response, String actor, Map<String, Object> extra) {
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
