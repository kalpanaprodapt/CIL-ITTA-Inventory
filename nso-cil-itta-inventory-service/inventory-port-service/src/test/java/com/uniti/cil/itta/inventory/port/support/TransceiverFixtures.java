package com.uniti.cil.itta.inventory.port.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Transceiver. */
public final class TransceiverFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TransceiverFixtures() {
    }

    public static TransceiverCreateRequest createRequest() {
        TransceiverCreateRequest request = new TransceiverCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setFormFactor(TransceiverFormFactor.SFP);
        request.setWavelengthNm(11);
        request.setReachKm(new BigDecimal("13.50"));
        request.setSerialNumber("serialNumber-1");
        request.setVendorPart("vendorPart-1");
        request.setLabels("labels-1");
        request.setLifecycleStage(TransceiverLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("TRANSC-0001");
        request.setName("Transceiver one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Transceiver entity() {
        Transceiver entity = new Transceiver();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setFormFactor(TransceiverFormFactor.SFPP);
        entity.setWavelengthNm(12);
        entity.setReachKm(new BigDecimal("14.50"));
        entity.setSerialNumber("serialNumber-2");
        entity.setVendorPart("vendorPart-2");
        entity.setLabels("labels-2");
        entity.setLifecycleStage(TransceiverLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("TRANSC-0002");
        entity.setName("Transceiver two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TransceiverResponse response() {
        TransceiverResponse response = new TransceiverResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TRANSC-0001");
        response.setName("Transceiver one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TransceiverSearchCriteria criteria() {
        TransceiverSearchCriteria criteria = new TransceiverSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
