package com.uniti.cil.itta.inventory.radio.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Antenna. */
public final class AntennaFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AntennaFixtures() {
    }

    public static AntennaCreateRequest createRequest() {
        AntennaCreateRequest request = new AntennaCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSectorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setModel("model-1");
        request.setHeightM(new BigDecimal("13.50"));
        request.setAzimuthDeg(new BigDecimal("13.50"));
        request.setTiltDeg(new BigDecimal("13.50"));
        request.setGainDbi(new BigDecimal("13.50"));
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(AntennaCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("ANTENN-0001");
        request.setName("Antenna one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Antenna entity() {
        Antenna entity = new Antenna();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSectorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setModel("model-2");
        entity.setHeightM(new BigDecimal("14.50"));
        entity.setAzimuthDeg(new BigDecimal("14.50"));
        entity.setTiltDeg(new BigDecimal("14.50"));
        entity.setGainDbi(new BigDecimal("14.50"));
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(AntennaCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("ANTENN-0002");
        entity.setName("Antenna two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AntennaResponse response() {
        AntennaResponse response = new AntennaResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ANTENN-0001");
        response.setName("Antenna one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AntennaSearchCriteria criteria() {
        AntennaSearchCriteria criteria = new AntennaSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
