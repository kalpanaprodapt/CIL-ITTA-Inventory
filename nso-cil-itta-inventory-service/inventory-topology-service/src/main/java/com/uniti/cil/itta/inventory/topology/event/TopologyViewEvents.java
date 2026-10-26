package com.uniti.cil.itta.inventory.topology.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.topology.dto.TopologyViewResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for TopologyView domain events published to the integration bus. */
public final class TopologyViewEvents {

    public static final String ENTITY_TYPE = "TopologyView";
    public static final String CREATED = "TopologyViewCreated";
    public static final String UPDATED = "TopologyViewUpdated";
    public static final String DELETED = "TopologyViewDeleted";
    public static final String STATUS_CHANGED = "TopologyViewStatusChanged";

    private TopologyViewEvents() {
    }

    public static DomainEvent created(TopologyViewResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(TopologyViewResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(TopologyViewResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(TopologyViewResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, TopologyViewResponse response, String actor, Map<String, Object> extra) {
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
