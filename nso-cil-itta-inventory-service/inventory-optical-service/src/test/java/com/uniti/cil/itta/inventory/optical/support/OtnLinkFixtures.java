package com.uniti.cil.itta.inventory.optical.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for OtnLink. */
public final class OtnLinkFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private OtnLinkFixtures() {
    }

    public static OtnLinkCreateRequest createRequest() {
        OtnLinkCreateRequest request = new OtnLinkCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setANodeId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZNodeId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setOdu(OtnLinkOdu.ODU0);
        request.setLatencyUs(new BigDecimal("13.50"));
        request.setProtection("protection-1");
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(OtnLinkLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("OTNLIN-0001");
        request.setName("Otn Link one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static OtnLink entity() {
        OtnLink entity = new OtnLink();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setANodeId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZNodeId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setOdu(OtnLinkOdu.ODU1);
        entity.setLatencyUs(new BigDecimal("14.50"));
        entity.setProtection("protection-2");
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(OtnLinkLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("OTNLIN-0002");
        entity.setName("Otn Link two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static OtnLinkResponse response() {
        OtnLinkResponse response = new OtnLinkResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("OTNLIN-0001");
        response.setName("Otn Link one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static OtnLinkSearchCriteria criteria() {
        OtnLinkSearchCriteria criteria = new OtnLinkSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
