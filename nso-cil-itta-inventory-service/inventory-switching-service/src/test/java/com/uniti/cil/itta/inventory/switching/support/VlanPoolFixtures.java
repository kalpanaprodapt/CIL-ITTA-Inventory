package com.uniti.cil.itta.inventory.switching.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for VlanPool. */
public final class VlanPoolFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VlanPoolFixtures() {
    }

    public static VlanPoolCreateRequest createRequest() {
        VlanPoolCreateRequest request = new VlanPoolCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPoolName("poolName-1");
        request.setRangeStart(11);
        request.setRangeEnd(11);
        request.setAllocated(11);
        request.setScope("scope-1");
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(VlanPoolLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("VLANPO-0001");
        request.setName("Vlan Pool one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static VlanPool entity() {
        VlanPool entity = new VlanPool();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPoolName("poolName-2");
        entity.setRangeStart(12);
        entity.setRangeEnd(12);
        entity.setAllocated(12);
        entity.setScope("scope-2");
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(VlanPoolLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("VLANPO-0002");
        entity.setName("Vlan Pool two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VlanPoolResponse response() {
        VlanPoolResponse response = new VlanPoolResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VLANPO-0001");
        response.setName("Vlan Pool one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VlanPoolSearchCriteria criteria() {
        VlanPoolSearchCriteria criteria = new VlanPoolSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
