package com.uniti.cil.itta.inventory.device.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Chassis. */
public final class ChassisFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ChassisFixtures() {
    }

    public static ChassisCreateRequest createRequest() {
        ChassisCreateRequest request = new ChassisCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSlotCount(11);
        request.setSerialNumber("serialNumber-1");
        request.setBackplaneGbps(new BigDecimal("13.50"));
        request.setRedundancyMode("redundancyMode-1");
        request.setLifecycleStage(ChassisLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ChassisCriticality.LOW);
        request.setCode("CHASSI-0001");
        request.setName("Chassis one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Chassis entity() {
        Chassis entity = new Chassis();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSlotCount(12);
        entity.setSerialNumber("serialNumber-2");
        entity.setBackplaneGbps(new BigDecimal("14.50"));
        entity.setRedundancyMode("redundancyMode-2");
        entity.setLifecycleStage(ChassisLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ChassisCriticality.MEDIUM);
        entity.setCode("CHASSI-0002");
        entity.setName("Chassis two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ChassisResponse response() {
        ChassisResponse response = new ChassisResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CHASSI-0001");
        response.setName("Chassis one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ChassisSearchCriteria criteria() {
        ChassisSearchCriteria criteria = new ChassisSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
