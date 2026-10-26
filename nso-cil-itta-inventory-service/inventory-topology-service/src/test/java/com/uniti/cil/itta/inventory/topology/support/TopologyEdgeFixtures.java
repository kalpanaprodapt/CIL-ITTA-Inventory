package com.uniti.cil.itta.inventory.topology.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for TopologyEdge. */
public final class TopologyEdgeFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TopologyEdgeFixtures() {
    }

    public static TopologyEdgeCreateRequest createRequest() {
        TopologyEdgeCreateRequest request = new TopologyEdgeCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSourceNodeId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTargetNodeId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setWeight(new BigDecimal("13.50"));
        request.setDirected(Boolean.TRUE);
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(TopologyEdgeLifecycleStage.PLAN);
        request.setCode("TOPOLO-0001");
        request.setName("Topology Edge one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static TopologyEdge entity() {
        TopologyEdge entity = new TopologyEdge();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSourceNodeId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTargetNodeId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setWeight(new BigDecimal("14.50"));
        entity.setDirected(Boolean.FALSE);
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(TopologyEdgeLifecycleStage.BUILD);
        entity.setCode("TOPOLO-0002");
        entity.setName("Topology Edge two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TopologyEdgeResponse response() {
        TopologyEdgeResponse response = new TopologyEdgeResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TOPOLO-0001");
        response.setName("Topology Edge one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TopologyEdgeSearchCriteria criteria() {
        TopologyEdgeSearchCriteria criteria = new TopologyEdgeSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
