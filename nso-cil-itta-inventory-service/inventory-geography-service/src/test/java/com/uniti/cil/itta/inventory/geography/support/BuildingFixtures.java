package com.uniti.cil.itta.inventory.geography.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Building. */
public final class BuildingFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private BuildingFixtures() {
    }

    public static BuildingCreateRequest createRequest() {
        BuildingCreateRequest request = new BuildingCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setFloors(11);
        request.setYearBuilt(11);
        request.setOwnership(BuildingOwnership.OWNED);
        request.setLeaseExpiry(LocalDate.of(2026, 2, 11));
        request.setSecurityLevel(11);
        request.setLifecycleStage(BuildingLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(BuildingCriticality.LOW);
        request.setCode("BUILDI-0001");
        request.setName("Building one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Building entity() {
        Building entity = new Building();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setFloors(12);
        entity.setYearBuilt(12);
        entity.setOwnership(BuildingOwnership.LEASED);
        entity.setLeaseExpiry(LocalDate.of(2026, 3, 12));
        entity.setSecurityLevel(12);
        entity.setLifecycleStage(BuildingLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(BuildingCriticality.MEDIUM);
        entity.setCode("BUILDI-0002");
        entity.setName("Building two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static BuildingResponse response() {
        BuildingResponse response = new BuildingResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("BUILDI-0001");
        response.setName("Building one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static BuildingSearchCriteria criteria() {
        BuildingSearchCriteria criteria = new BuildingSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
