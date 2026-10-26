package com.uniti.cil.itta.inventory.optical.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Roadm. */
public final class RoadmFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RoadmFixtures() {
    }

    public static RoadmCreateRequest createRequest() {
        RoadmCreateRequest request = new RoadmCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setDegrees(11);
        request.setColorless(Boolean.TRUE);
        request.setDirectionless(Boolean.TRUE);
        request.setContentionless(Boolean.TRUE);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(RoadmCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("ROADM-0001");
        request.setName("Roadm one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Roadm entity() {
        Roadm entity = new Roadm();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setDegrees(12);
        entity.setColorless(Boolean.FALSE);
        entity.setDirectionless(Boolean.FALSE);
        entity.setContentionless(Boolean.FALSE);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(RoadmCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("ROADM-0002");
        entity.setName("Roadm two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RoadmResponse response() {
        RoadmResponse response = new RoadmResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ROADM-0001");
        response.setName("Roadm one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RoadmSearchCriteria criteria() {
        RoadmSearchCriteria criteria = new RoadmSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
