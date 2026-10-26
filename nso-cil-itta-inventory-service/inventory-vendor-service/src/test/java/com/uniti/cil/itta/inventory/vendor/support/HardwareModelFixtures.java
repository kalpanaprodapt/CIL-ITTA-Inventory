package com.uniti.cil.itta.inventory.vendor.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for HardwareModel. */
public final class HardwareModelFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private HardwareModelFixtures() {
    }

    public static HardwareModelCreateRequest createRequest() {
        HardwareModelCreateRequest request = new HardwareModelCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setModelNumber("modelNumber-1");
        request.setFormFactor("formFactor-1");
        request.setPowerWatts(11);
        request.setEolDate(LocalDate.of(2026, 2, 11));
        request.setEosDate(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(HardwareModelLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("HARDWA-0001");
        request.setName("Hardware Model one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static HardwareModel entity() {
        HardwareModel entity = new HardwareModel();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setModelNumber("modelNumber-2");
        entity.setFormFactor("formFactor-2");
        entity.setPowerWatts(12);
        entity.setEolDate(LocalDate.of(2026, 3, 12));
        entity.setEosDate(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(HardwareModelLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("HARDWA-0002");
        entity.setName("Hardware Model two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static HardwareModelResponse response() {
        HardwareModelResponse response = new HardwareModelResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("HARDWA-0001");
        response.setName("Hardware Model one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static HardwareModelSearchCriteria criteria() {
        HardwareModelSearchCriteria criteria = new HardwareModelSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
