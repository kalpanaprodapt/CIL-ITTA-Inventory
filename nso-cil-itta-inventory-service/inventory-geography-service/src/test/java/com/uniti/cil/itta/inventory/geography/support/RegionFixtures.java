package com.uniti.cil.itta.inventory.geography.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Region. */
public final class RegionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RegionFixtures() {
    }

    public static RegionCreateRequest createRequest() {
        RegionCreateRequest request = new RegionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCountryId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRegionType(RegionRegionType.STATE);
        request.setPopulation(1001L);
        request.setLatitude(new BigDecimal("13.50"));
        request.setLongitude(new BigDecimal("13.50"));
        request.setCriticality(RegionCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("REGION-0001");
        request.setName("Region one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Region entity() {
        Region entity = new Region();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCountryId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRegionType(RegionRegionType.PROVINCE);
        entity.setPopulation(1002L);
        entity.setLatitude(new BigDecimal("14.50"));
        entity.setLongitude(new BigDecimal("14.50"));
        entity.setCriticality(RegionCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("REGION-0002");
        entity.setName("Region two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RegionResponse response() {
        RegionResponse response = new RegionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("REGION-0001");
        response.setName("Region one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RegionSearchCriteria criteria() {
        RegionSearchCriteria criteria = new RegionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
