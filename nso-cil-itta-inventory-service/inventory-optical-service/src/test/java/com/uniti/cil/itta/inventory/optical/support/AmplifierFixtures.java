package com.uniti.cil.itta.inventory.optical.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Amplifier. */
public final class AmplifierFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AmplifierFixtures() {
    }

    public static AmplifierCreateRequest createRequest() {
        AmplifierCreateRequest request = new AmplifierCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAmpType(AmplifierAmpType.EDFA);
        request.setGainDb(new BigDecimal("13.50"));
        request.setOutputPowerDbm(new BigDecimal("13.50"));
        request.setNoiseFigureDb(new BigDecimal("13.50"));
        request.setLifecycleStage(AmplifierLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(AmplifierCriticality.LOW);
        request.setCode("AMPLIF-0001");
        request.setName("Amplifier one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Amplifier entity() {
        Amplifier entity = new Amplifier();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAmpType(AmplifierAmpType.RAMAN);
        entity.setGainDb(new BigDecimal("14.50"));
        entity.setOutputPowerDbm(new BigDecimal("14.50"));
        entity.setNoiseFigureDb(new BigDecimal("14.50"));
        entity.setLifecycleStage(AmplifierLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(AmplifierCriticality.MEDIUM);
        entity.setCode("AMPLIF-0002");
        entity.setName("Amplifier two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AmplifierResponse response() {
        AmplifierResponse response = new AmplifierResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("AMPLIF-0001");
        response.setName("Amplifier one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AmplifierSearchCriteria criteria() {
        AmplifierSearchCriteria criteria = new AmplifierSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
