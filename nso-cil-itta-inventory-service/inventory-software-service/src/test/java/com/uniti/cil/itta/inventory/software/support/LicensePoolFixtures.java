package com.uniti.cil.itta.inventory.software.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for LicensePool. */
public final class LicensePoolFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private LicensePoolFixtures() {
    }

    public static LicensePoolCreateRequest createRequest() {
        LicensePoolCreateRequest request = new LicensePoolCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPoolName("poolName-1");
        request.setTotalSeats(11);
        request.setUsedSeats(11);
        request.setRenewalDate(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(LicensePoolLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("LICENS-0001");
        request.setName("License Pool one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static LicensePool entity() {
        LicensePool entity = new LicensePool();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPoolName("poolName-2");
        entity.setTotalSeats(12);
        entity.setUsedSeats(12);
        entity.setRenewalDate(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(LicensePoolLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("LICENS-0002");
        entity.setName("License Pool two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static LicensePoolResponse response() {
        LicensePoolResponse response = new LicensePoolResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("LICENS-0001");
        response.setName("License Pool one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static LicensePoolSearchCriteria criteria() {
        LicensePoolSearchCriteria criteria = new LicensePoolSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
