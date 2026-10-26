package com.uniti.cil.itta.inventory.facility.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PowerFeed. */
public final class PowerFeedFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PowerFeedFixtures() {
    }

    public static PowerFeedCreateRequest createRequest() {
        PowerFeedCreateRequest request = new PowerFeedCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setFeedType(PowerFeedFeedType.AC);
        request.setVoltage(new BigDecimal("13.50"));
        request.setAmperage(new BigDecimal("13.50"));
        request.setPhase("phase-1");
        request.setBreakerLabel("breakerLabel-1");
        request.setRedundancyGroup("redundancyGroup-1");
        request.setOwner("owner-1");
        request.setCriticality(PowerFeedCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("POWERF-0001");
        request.setName("Power Feed one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PowerFeed entity() {
        PowerFeed entity = new PowerFeed();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setFeedType(PowerFeedFeedType.DC);
        entity.setVoltage(new BigDecimal("14.50"));
        entity.setAmperage(new BigDecimal("14.50"));
        entity.setPhase("phase-2");
        entity.setBreakerLabel("breakerLabel-2");
        entity.setRedundancyGroup("redundancyGroup-2");
        entity.setOwner("owner-2");
        entity.setCriticality(PowerFeedCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("POWERF-0002");
        entity.setName("Power Feed two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PowerFeedResponse response() {
        PowerFeedResponse response = new PowerFeedResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("POWERF-0001");
        response.setName("Power Feed one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PowerFeedSearchCriteria criteria() {
        PowerFeedSearchCriteria criteria = new PowerFeedSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
