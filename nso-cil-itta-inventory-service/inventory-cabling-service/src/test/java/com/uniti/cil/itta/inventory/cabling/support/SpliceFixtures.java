package com.uniti.cil.itta.inventory.cabling.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Splice. */
public final class SpliceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SpliceFixtures() {
    }

    public static SpliceCreateRequest createRequest() {
        SpliceCreateRequest request = new SpliceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setEnclosureId("enclosureId-1");
        request.setAStrandId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZStrandId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLossDb(new BigDecimal("13.50"));
        request.setSplicedOn(LocalDate.of(2026, 2, 11));
        request.setLifecycleStage(SpliceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(SpliceCriticality.LOW);
        request.setCode("SPLICE-0001");
        request.setName("Splice one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Splice entity() {
        Splice entity = new Splice();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setEnclosureId("enclosureId-2");
        entity.setAStrandId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZStrandId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLossDb(new BigDecimal("14.50"));
        entity.setSplicedOn(LocalDate.of(2026, 3, 12));
        entity.setLifecycleStage(SpliceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SpliceCriticality.MEDIUM);
        entity.setCode("SPLICE-0002");
        entity.setName("Splice two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SpliceResponse response() {
        SpliceResponse response = new SpliceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SPLICE-0001");
        response.setName("Splice one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SpliceSearchCriteria criteria() {
        SpliceSearchCriteria criteria = new SpliceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
