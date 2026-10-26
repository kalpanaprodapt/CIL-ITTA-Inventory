package com.uniti.cil.itta.inventory.topology.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for TopologyView. */
public final class TopologyViewFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TopologyViewFixtures() {
    }

    public static TopologyViewCreateRequest createRequest() {
        TopologyViewCreateRequest request = new TopologyViewCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setViewName("viewName-1");
        request.setViewType(TopologyViewViewType.PHYSICAL);
        request.setScopeFilter("scopeFilter-1");
        request.setRefreshedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(TopologyViewLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("TOPOLO-0001");
        request.setName("Topology View one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static TopologyView entity() {
        TopologyView entity = new TopologyView();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setViewName("viewName-2");
        entity.setViewType(TopologyViewViewType.L2);
        entity.setScopeFilter("scopeFilter-2");
        entity.setRefreshedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(TopologyViewLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("TOPOLO-0002");
        entity.setName("Topology View two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TopologyViewResponse response() {
        TopologyViewResponse response = new TopologyViewResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TOPOLO-0001");
        response.setName("Topology View one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TopologyViewSearchCriteria criteria() {
        TopologyViewSearchCriteria criteria = new TopologyViewSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
