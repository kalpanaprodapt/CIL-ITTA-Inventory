package com.uniti.cil.itta.inventory.geography.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for City. */
public final class CityFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CityFixtures() {
    }

    public static CityCreateRequest createRequest() {
        CityCreateRequest request = new CityCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRegionId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPostalPrefix("postalPrefix-1");
        request.setPopulation(1001L);
        request.setLatitude(new BigDecimal("13.50"));
        request.setLongitude(new BigDecimal("13.50"));
        request.setMetroArea("metroArea-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(CityLifecycleStage.PLAN);
        request.setCode("CITY-0001");
        request.setName("City one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static City entity() {
        City entity = new City();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRegionId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPostalPrefix("postalPrefix-2");
        entity.setPopulation(1002L);
        entity.setLatitude(new BigDecimal("14.50"));
        entity.setLongitude(new BigDecimal("14.50"));
        entity.setMetroArea("metroArea-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CityLifecycleStage.BUILD);
        entity.setCode("CITY-0002");
        entity.setName("City two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CityResponse response() {
        CityResponse response = new CityResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CITY-0001");
        response.setName("City one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CitySearchCriteria criteria() {
        CitySearchCriteria criteria = new CitySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
