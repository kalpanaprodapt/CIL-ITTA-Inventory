package com.uniti.cil.itta.inventory.optical.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for WdmSystem. */
public final class WdmSystemFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private WdmSystemFixtures() {
    }

    public static WdmSystemCreateRequest createRequest() {
        WdmSystemCreateRequest request = new WdmSystemCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSystemName("systemName-1");
        request.setChannelCapacity(11);
        request.setBand(WdmSystemBand.C);
        request.setSpanCount(11);
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(WdmSystemLifecycleStage.PLAN);
        request.setCode("WDMSYS-0001");
        request.setName("Wdm System one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static WdmSystem entity() {
        WdmSystem entity = new WdmSystem();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSystemName("systemName-2");
        entity.setChannelCapacity(12);
        entity.setBand(WdmSystemBand.L);
        entity.setSpanCount(12);
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(WdmSystemLifecycleStage.BUILD);
        entity.setCode("WDMSYS-0002");
        entity.setName("Wdm System two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static WdmSystemResponse response() {
        WdmSystemResponse response = new WdmSystemResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("WDMSYS-0001");
        response.setName("Wdm System one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static WdmSystemSearchCriteria criteria() {
        WdmSystemSearchCriteria criteria = new WdmSystemSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
