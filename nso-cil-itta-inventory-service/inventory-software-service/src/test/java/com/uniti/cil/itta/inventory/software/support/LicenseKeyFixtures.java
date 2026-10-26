package com.uniti.cil.itta.inventory.software.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for LicenseKey. */
public final class LicenseKeyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private LicenseKeyFixtures() {
    }

    public static LicenseKeyCreateRequest createRequest() {
        LicenseKeyCreateRequest request = new LicenseKeyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setKeyValue("keyValue-1");
        request.setFeature("feature-1");
        request.setExpiresOn(LocalDate.of(2026, 2, 11));
        request.setAssignedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(LicenseKeyLifecycleStage.PLAN);
        request.setCode("LICENS-0001");
        request.setName("License Key one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static LicenseKey entity() {
        LicenseKey entity = new LicenseKey();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setKeyValue("keyValue-2");
        entity.setFeature("feature-2");
        entity.setExpiresOn(LocalDate.of(2026, 3, 12));
        entity.setAssignedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(LicenseKeyLifecycleStage.BUILD);
        entity.setCode("LICENS-0002");
        entity.setName("License Key two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static LicenseKeyResponse response() {
        LicenseKeyResponse response = new LicenseKeyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("LICENS-0001");
        response.setName("License Key one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static LicenseKeySearchCriteria criteria() {
        LicenseKeySearchCriteria criteria = new LicenseKeySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
