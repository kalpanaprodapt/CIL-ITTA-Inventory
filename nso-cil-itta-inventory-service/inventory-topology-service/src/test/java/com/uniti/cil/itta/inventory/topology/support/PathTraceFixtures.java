package com.uniti.cil.itta.inventory.topology.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PathTrace. */
public final class PathTraceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PathTraceFixtures() {
    }

    public static PathTraceCreateRequest createRequest() {
        PathTraceCreateRequest request = new PathTraceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSourceRef("sourceRef-1");
        request.setTargetRef("targetRef-1");
        request.setHopCount(11);
        request.setTotalLatencyMs(new BigDecimal("13.50"));
        request.setTraceMethod("traceMethod-1");
        request.setTracedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setLifecycleStage(PathTraceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(PathTraceCriticality.LOW);
        request.setCode("PATHTR-0001");
        request.setName("Path Trace one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PathTrace entity() {
        PathTrace entity = new PathTrace();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSourceRef("sourceRef-2");
        entity.setTargetRef("targetRef-2");
        entity.setHopCount(12);
        entity.setTotalLatencyMs(new BigDecimal("14.50"));
        entity.setTraceMethod("traceMethod-2");
        entity.setTracedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setLifecycleStage(PathTraceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(PathTraceCriticality.MEDIUM);
        entity.setCode("PATHTR-0002");
        entity.setName("Path Trace two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PathTraceResponse response() {
        PathTraceResponse response = new PathTraceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PATHTR-0001");
        response.setName("Path Trace one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PathTraceSearchCriteria criteria() {
        PathTraceSearchCriteria criteria = new PathTraceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
