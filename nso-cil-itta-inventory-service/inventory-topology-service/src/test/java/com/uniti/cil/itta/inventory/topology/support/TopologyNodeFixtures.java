package com.uniti.cil.itta.inventory.topology.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for TopologyNode. */
public final class TopologyNodeFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TopologyNodeFixtures() {
    }

    public static TopologyNodeCreateRequest createRequest() {
        TopologyNodeCreateRequest request = new TopologyNodeCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEntityRef("entityRef-1");
        request.setNodeType("nodeType-1");
        request.setXPos(new BigDecimal("13.50"));
        request.setYPos(new BigDecimal("13.50"));
        request.setLayer(11);
        request.setCriticality(TopologyNodeCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("TOPOLO-0001");
        request.setName("Topology Node one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static TopologyNode entity() {
        TopologyNode entity = new TopologyNode();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEntityRef("entityRef-2");
        entity.setNodeType("nodeType-2");
        entity.setXPos(new BigDecimal("14.50"));
        entity.setYPos(new BigDecimal("14.50"));
        entity.setLayer(12);
        entity.setCriticality(TopologyNodeCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("TOPOLO-0002");
        entity.setName("Topology Node two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TopologyNodeResponse response() {
        TopologyNodeResponse response = new TopologyNodeResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TOPOLO-0001");
        response.setName("Topology Node one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TopologyNodeSearchCriteria criteria() {
        TopologyNodeSearchCriteria criteria = new TopologyNodeSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
