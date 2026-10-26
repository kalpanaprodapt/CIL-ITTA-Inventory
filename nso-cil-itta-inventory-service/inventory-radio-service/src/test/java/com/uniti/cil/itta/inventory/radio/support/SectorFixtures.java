package com.uniti.cil.itta.inventory.radio.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Sector. */
public final class SectorFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SectorFixtures() {
    }

    public static SectorCreateRequest createRequest() {
        SectorCreateRequest request = new SectorCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSectorNumber(11);
        request.setPci(11);
        request.setFrequencyMhz(11);
        request.setBandwidthMhz(11);
        request.setOwner("owner-1");
        request.setCriticality(SectorCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("SECTOR-0001");
        request.setName("Sector one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Sector entity() {
        Sector entity = new Sector();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSectorNumber(12);
        entity.setPci(12);
        entity.setFrequencyMhz(12);
        entity.setBandwidthMhz(12);
        entity.setOwner("owner-2");
        entity.setCriticality(SectorCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("SECTOR-0002");
        entity.setName("Sector two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SectorResponse response() {
        SectorResponse response = new SectorResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SECTOR-0001");
        response.setName("Sector one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SectorSearchCriteria criteria() {
        SectorSearchCriteria criteria = new SectorSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
