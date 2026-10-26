package com.uniti.cil.itta.inventory.assetmgmt.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for DepreciationSchedule. */
public final class DepreciationScheduleFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private DepreciationScheduleFixtures() {
    }

    public static DepreciationScheduleCreateRequest createRequest() {
        DepreciationScheduleCreateRequest request = new DepreciationScheduleCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setMethod(DepreciationScheduleMethod.STRAIGHT_LINE);
        request.setUsefulLifeMonths(11);
        request.setSalvageValue(new BigDecimal("13.50"));
        request.setStartDate(LocalDate.of(2026, 2, 11));
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(DepreciationScheduleLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("DEPREC-0001");
        request.setName("Depreciation Schedule one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static DepreciationSchedule entity() {
        DepreciationSchedule entity = new DepreciationSchedule();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setMethod(DepreciationScheduleMethod.DECLINING);
        entity.setUsefulLifeMonths(12);
        entity.setSalvageValue(new BigDecimal("14.50"));
        entity.setStartDate(LocalDate.of(2026, 3, 12));
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(DepreciationScheduleLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("DEPREC-0002");
        entity.setName("Depreciation Schedule two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static DepreciationScheduleResponse response() {
        DepreciationScheduleResponse response = new DepreciationScheduleResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("DEPREC-0001");
        response.setName("Depreciation Schedule one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static DepreciationScheduleSearchCriteria criteria() {
        DepreciationScheduleSearchCriteria criteria = new DepreciationScheduleSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
