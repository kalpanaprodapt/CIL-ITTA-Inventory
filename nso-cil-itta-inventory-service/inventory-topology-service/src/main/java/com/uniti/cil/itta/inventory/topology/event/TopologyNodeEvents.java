package com.uniti.cil.itta.inventory.topology.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.topology.dto.TopologyNodeResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for TopologyNode domain events published to the integration bus. */
public final class TopologyNodeEvents {

    public static final String ENTITY_TYPE = "TopologyNode";
    public static final String CREATED = "TopologyNodeCreated";
    public static final String UPDATED = "TopologyNodeUpdated";
    public static final String DELETED = "TopologyNodeDeleted";
    public static final String STATUS_CHANGED = "TopologyNodeStatusChanged";

    private TopologyNodeEvents() {
    }

    public static DomainEvent created(TopologyNodeResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(TopologyNodeResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(TopologyNodeResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(TopologyNodeResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, TopologyNodeResponse response, String actor, Map<String, Object> extra) {
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
