package com.uniti.cil.itta.inventory.port.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for LagGroup. */
public final class LagGroupFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private LagGroupFixtures() {
    }

    public static LagGroupCreateRequest createRequest() {
        LagGroupCreateRequest request = new LagGroupCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLagName("lagName-1");
        request.setMemberCount(11);
        request.setLacpMode(LagGroupLacpMode.ACTIVE);
        request.setMinLinks(11);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(LagGroupLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("LAGGRO-0001");
        request.setName("Lag Group one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static LagGroup entity() {
        LagGroup entity = new LagGroup();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLagName("lagName-2");
        entity.setMemberCount(12);
        entity.setLacpMode(LagGroupLacpMode.PASSIVE);
        entity.setMinLinks(12);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(LagGroupLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("LAGGRO-0002");
        entity.setName("Lag Group two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static LagGroupResponse response() {
        LagGroupResponse response = new LagGroupResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("LAGGRO-0001");
        response.setName("Lag Group one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static LagGroupSearchCriteria criteria() {
        LagGroupSearchCriteria criteria = new LagGroupSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
