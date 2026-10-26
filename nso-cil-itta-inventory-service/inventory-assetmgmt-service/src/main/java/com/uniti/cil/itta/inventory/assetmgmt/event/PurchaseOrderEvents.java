package com.uniti.cil.itta.inventory.assetmgmt.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.assetmgmt.dto.PurchaseOrderResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for PurchaseOrder domain events published to the integration bus. */
public final class PurchaseOrderEvents {

    public static final String ENTITY_TYPE = "PurchaseOrder";
    public static final String CREATED = "PurchaseOrderCreated";
    public static final String UPDATED = "PurchaseOrderUpdated";
    public static final String DELETED = "PurchaseOrderDeleted";
    public static final String STATUS_CHANGED = "PurchaseOrderStatusChanged";

    private PurchaseOrderEvents() {
    }

    public static DomainEvent created(PurchaseOrderResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(PurchaseOrderResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(PurchaseOrderResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(PurchaseOrderResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, PurchaseOrderResponse response, String actor, Map<String, Object> extra) {
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
