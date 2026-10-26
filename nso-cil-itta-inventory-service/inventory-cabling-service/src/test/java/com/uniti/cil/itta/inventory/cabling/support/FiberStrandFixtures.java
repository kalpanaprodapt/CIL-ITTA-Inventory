package com.uniti.cil.itta.inventory.cabling.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for FiberStrand. */
public final class FiberStrandFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private FiberStrandFixtures() {
    }

    public static FiberStrandCreateRequest createRequest() {
        FiberStrandCreateRequest request = new FiberStrandCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCableId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setStrandNumber(11);
        request.setBufferTube("bufferTube-1");
        request.setTubeColor("tubeColor-1");
        request.setAttenuationDb(new BigDecimal("13.50"));
        request.setInUse(Boolean.TRUE);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(FiberStrandCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("FIBERS-0001");
        request.setName("Fiber Strand one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static FiberStrand entity() {
        FiberStrand entity = new FiberStrand();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCableId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setStrandNumber(12);
        entity.setBufferTube("bufferTube-2");
        entity.setTubeColor("tubeColor-2");
        entity.setAttenuationDb(new BigDecimal("14.50"));
        entity.setInUse(Boolean.FALSE);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(FiberStrandCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("FIBERS-0002");
        entity.setName("Fiber Strand two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static FiberStrandResponse response() {
        FiberStrandResponse response = new FiberStrandResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("FIBERS-0001");
        response.setName("Fiber Strand one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static FiberStrandSearchCriteria criteria() {
        FiberStrandSearchCriteria criteria = new FiberStrandSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
