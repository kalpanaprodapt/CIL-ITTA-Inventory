package com.uniti.cil.itta.inventory.device.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PowerSupply. */
public final class PowerSupplyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PowerSupplyFixtures() {
    }

    public static PowerSupplyCreateRequest createRequest() {
        PowerSupplyCreateRequest request = new PowerSupplyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSlotNumber(11);
        request.setWattage(11);
        request.setFeedId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSerialNumber("serialNumber-1");
        request.setEfficiency(new BigDecimal("13.50"));
        request.setCriticality(PowerSupplyCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("POWERS-0001");
        request.setName("Power Supply one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PowerSupply entity() {
        PowerSupply entity = new PowerSupply();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSlotNumber(12);
        entity.setWattage(12);
        entity.setFeedId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSerialNumber("serialNumber-2");
        entity.setEfficiency(new BigDecimal("14.50"));
        entity.setCriticality(PowerSupplyCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("POWERS-0002");
        entity.setName("Power Supply two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PowerSupplyResponse response() {
        PowerSupplyResponse response = new PowerSupplyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("POWERS-0001");
        response.setName("Power Supply one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PowerSupplySearchCriteria criteria() {
        PowerSupplySearchCriteria criteria = new PowerSupplySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
