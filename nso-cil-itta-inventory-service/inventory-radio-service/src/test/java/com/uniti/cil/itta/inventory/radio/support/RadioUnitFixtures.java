package com.uniti.cil.itta.inventory.radio.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for RadioUnit. */
public final class RadioUnitFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RadioUnitFixtures() {
    }

    public static RadioUnitCreateRequest createRequest() {
        RadioUnitCreateRequest request = new RadioUnitCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setUnitType(RadioUnitUnitType.RRU);
        request.setBandMhz(11);
        request.setTxPowerDbm(new BigDecimal("13.50"));
        request.setSerialNumber("serialNumber-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(RadioUnitLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("RADIOU-0001");
        request.setName("Radio Unit one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static RadioUnit entity() {
        RadioUnit entity = new RadioUnit();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setUnitType(RadioUnitUnitType.AAU);
        entity.setBandMhz(12);
        entity.setTxPowerDbm(new BigDecimal("14.50"));
        entity.setSerialNumber("serialNumber-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(RadioUnitLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("RADIOU-0002");
        entity.setName("Radio Unit two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RadioUnitResponse response() {
        RadioUnitResponse response = new RadioUnitResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RADIOU-0001");
        response.setName("Radio Unit one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RadioUnitSearchCriteria criteria() {
        RadioUnitSearchCriteria criteria = new RadioUnitSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
