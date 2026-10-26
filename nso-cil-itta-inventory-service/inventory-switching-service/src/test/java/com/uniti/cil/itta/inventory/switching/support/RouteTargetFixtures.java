package com.uniti.cil.itta.inventory.switching.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for RouteTarget. */
public final class RouteTargetFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RouteTargetFixtures() {
    }

    public static RouteTargetCreateRequest createRequest() {
        RouteTargetCreateRequest request = new RouteTargetCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAsn(1001L);
        request.setAssignedNumber(1001L);
        request.setRtType(RouteTargetRtType.IMPORT);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLifecycleStage(RouteTargetLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(RouteTargetCriticality.LOW);
        request.setCode("ROUTET-0001");
        request.setName("Route Target one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static RouteTarget entity() {
        RouteTarget entity = new RouteTarget();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAsn(1002L);
        entity.setAssignedNumber(1002L);
        entity.setRtType(RouteTargetRtType.EXPORT);
        entity.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLifecycleStage(RouteTargetLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(RouteTargetCriticality.MEDIUM);
        entity.setCode("ROUTET-0002");
        entity.setName("Route Target two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RouteTargetResponse response() {
        RouteTargetResponse response = new RouteTargetResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ROUTET-0001");
        response.setName("Route Target one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RouteTargetSearchCriteria criteria() {
        RouteTargetSearchCriteria criteria = new RouteTargetSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
