package com.uniti.cil.itta.inventory.device.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for FanTray. */
public final class FanTrayFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private FanTrayFixtures() {
    }

    public static FanTrayCreateRequest createRequest() {
        FanTrayCreateRequest request = new FanTrayCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSlotNumber(11);
        request.setFanCount(11);
        request.setAirflow(FanTrayAirflow.FRONT_TO_BACK);
        request.setSerialNumber("serialNumber-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(FanTrayLifecycleStage.PLAN);
        request.setCode("FANTRA-0001");
        request.setName("Fan Tray one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static FanTray entity() {
        FanTray entity = new FanTray();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSlotNumber(12);
        entity.setFanCount(12);
        entity.setAirflow(FanTrayAirflow.BACK_TO_FRONT);
        entity.setSerialNumber("serialNumber-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(FanTrayLifecycleStage.BUILD);
        entity.setCode("FANTRA-0002");
        entity.setName("Fan Tray two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static FanTrayResponse response() {
        FanTrayResponse response = new FanTrayResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("FANTRA-0001");
        response.setName("Fan Tray one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static FanTraySearchCriteria criteria() {
        FanTraySearchCriteria criteria = new FanTraySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
