package com.uniti.cil.itta.inventory.radio.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for BaseStation. */
public final class BaseStationFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private BaseStationFixtures() {
    }

    public static BaseStationCreateRequest createRequest() {
        BaseStationCreateRequest request = new BaseStationCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEnbId(1001L);
        request.setTechnology(BaseStationTechnology.LTE);
        request.setSectorCount(11);
        request.setCellCount(11);
        request.setLabels("labels-1");
        request.setLifecycleStage(BaseStationLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("BASEST-0001");
        request.setName("Base Station one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static BaseStation entity() {
        BaseStation entity = new BaseStation();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEnbId(1002L);
        entity.setTechnology(BaseStationTechnology.NR);
        entity.setSectorCount(12);
        entity.setCellCount(12);
        entity.setLabels("labels-2");
        entity.setLifecycleStage(BaseStationLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("BASEST-0002");
        entity.setName("Base Station two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static BaseStationResponse response() {
        BaseStationResponse response = new BaseStationResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("BASEST-0001");
        response.setName("Base Station one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static BaseStationSearchCriteria criteria() {
        BaseStationSearchCriteria criteria = new BaseStationSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
