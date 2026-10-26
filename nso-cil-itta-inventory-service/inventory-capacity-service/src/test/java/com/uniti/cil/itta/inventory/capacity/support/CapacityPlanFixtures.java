package com.uniti.cil.itta.inventory.capacity.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CapacityPlan. */
public final class CapacityPlanFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CapacityPlanFixtures() {
    }

    public static CapacityPlanCreateRequest createRequest() {
        CapacityPlanCreateRequest request = new CapacityPlanCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPlanName("planName-1");
        request.setHorizonMonths(11);
        request.setTargetUtilizationPct(new BigDecimal("13.50"));
        request.setOwner("owner-1");
        request.setApprovedOn(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(CapacityPlanLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("CAPACI-0001");
        request.setName("Capacity Plan one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CapacityPlan entity() {
        CapacityPlan entity = new CapacityPlan();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPlanName("planName-2");
        entity.setHorizonMonths(12);
        entity.setTargetUtilizationPct(new BigDecimal("14.50"));
        entity.setOwner("owner-2");
        entity.setApprovedOn(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CapacityPlanLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("CAPACI-0002");
        entity.setName("Capacity Plan two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CapacityPlanResponse response() {
        CapacityPlanResponse response = new CapacityPlanResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CAPACI-0001");
        response.setName("Capacity Plan one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CapacityPlanSearchCriteria criteria() {
        CapacityPlanSearchCriteria criteria = new CapacityPlanSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
