package com.uniti.cil.itta.inventory.capacity.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for UtilizationSample. */
public final class UtilizationSampleFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private UtilizationSampleFixtures() {
    }

    public static UtilizationSampleCreateRequest createRequest() {
        UtilizationSampleCreateRequest request = new UtilizationSampleCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setTargetRef("targetRef-1");
        request.setMetric("metric-1");
        request.setSampledAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setValuePct(new BigDecimal("13.50"));
        request.setPeak(Boolean.TRUE);
        request.setLifecycleStage(UtilizationSampleLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(UtilizationSampleCriticality.LOW);
        request.setCode("UTILIZ-0001");
        request.setName("Utilization Sample one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static UtilizationSample entity() {
        UtilizationSample entity = new UtilizationSample();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setTargetRef("targetRef-2");
        entity.setMetric("metric-2");
        entity.setSampledAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setValuePct(new BigDecimal("14.50"));
        entity.setPeak(Boolean.FALSE);
        entity.setLifecycleStage(UtilizationSampleLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(UtilizationSampleCriticality.MEDIUM);
        entity.setCode("UTILIZ-0002");
        entity.setName("Utilization Sample two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static UtilizationSampleResponse response() {
        UtilizationSampleResponse response = new UtilizationSampleResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("UTILIZ-0001");
        response.setName("Utilization Sample one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static UtilizationSampleSearchCriteria criteria() {
        UtilizationSampleSearchCriteria criteria = new UtilizationSampleSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
