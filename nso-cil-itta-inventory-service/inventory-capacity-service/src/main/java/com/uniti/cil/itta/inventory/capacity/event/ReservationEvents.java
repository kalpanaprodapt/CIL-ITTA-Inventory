package com.uniti.cil.itta.inventory.capacity.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.capacity.dto.ReservationResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Reservation domain events published to the integration bus. */
public final class ReservationEvents {

    public static final String ENTITY_TYPE = "Reservation";
    public static final String CREATED = "ReservationCreated";
    public static final String UPDATED = "ReservationUpdated";
    public static final String DELETED = "ReservationDeleted";
    public static final String STATUS_CHANGED = "ReservationStatusChanged";

    private ReservationEvents() {
    }

    public static DomainEvent created(ReservationResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ReservationResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ReservationResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ReservationResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ReservationResponse response, String actor, Map<String, Object> extra) {
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
