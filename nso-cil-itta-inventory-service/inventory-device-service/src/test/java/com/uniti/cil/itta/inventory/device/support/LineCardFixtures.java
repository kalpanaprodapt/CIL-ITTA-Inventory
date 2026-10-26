package com.uniti.cil.itta.inventory.device.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for LineCard. */
public final class LineCardFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private LineCardFixtures() {
    }

    public static LineCardCreateRequest createRequest() {
        LineCardCreateRequest request = new LineCardCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSlotNumber(11);
        request.setCardType("cardType-1");
        request.setPortCount(11);
        request.setSerialNumber("serialNumber-1");
        request.setFirmware("firmware-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(LineCardCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("LINECA-0001");
        request.setName("Line Card one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static LineCard entity() {
        LineCard entity = new LineCard();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSlotNumber(12);
        entity.setCardType("cardType-2");
        entity.setPortCount(12);
        entity.setSerialNumber("serialNumber-2");
        entity.setFirmware("firmware-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(LineCardCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("LINECA-0002");
        entity.setName("Line Card two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static LineCardResponse response() {
        LineCardResponse response = new LineCardResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("LINECA-0001");
        response.setName("Line Card one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static LineCardSearchCriteria criteria() {
        LineCardSearchCriteria criteria = new LineCardSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
