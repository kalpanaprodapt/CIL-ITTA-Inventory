package com.uniti.cil.itta.inventory.geography.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Site. */
public final class SiteFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SiteFixtures() {
    }

    public static SiteCreateRequest createRequest() {
        SiteCreateRequest request = new SiteCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCityId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSiteType(SiteSiteType.CO);
        request.setAddress("address-1");
        request.setLatitude(new BigDecimal("13.50"));
        request.setLongitude(new BigDecimal("13.50"));
        request.setPowerKw(new BigDecimal("13.50"));
        request.setAccessHours("accessHours-1");
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(SiteLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("SITE-0001");
        request.setName("Site one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Site entity() {
        Site entity = new Site();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCityId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSiteType(SiteSiteType.POP);
        entity.setAddress("address-2");
        entity.setLatitude(new BigDecimal("14.50"));
        entity.setLongitude(new BigDecimal("14.50"));
        entity.setPowerKw(new BigDecimal("14.50"));
        entity.setAccessHours("accessHours-2");
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(SiteLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("SITE-0002");
        entity.setName("Site two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SiteResponse response() {
        SiteResponse response = new SiteResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SITE-0001");
        response.setName("Site one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SiteSearchCriteria criteria() {
        SiteSearchCriteria criteria = new SiteSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
