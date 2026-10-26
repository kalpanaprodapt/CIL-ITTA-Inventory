package com.uniti.cil.itta.inventory.facility.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Rack. */
public final class RackFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RackFixtures() {
    }

    public static RackCreateRequest createRequest() {
        RackCreateRequest request = new RackCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRackUnits(11);
        request.setWidthMm(11);
        request.setDepthMm(11);
        request.setMaxWeightKg(new BigDecimal("13.50"));
        request.setRowLabel("rowLabel-1");
        request.setPosition("position-1");
        request.setLabels("labels-1");
        request.setLifecycleStage(RackLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("RACK-0001");
        request.setName("Rack one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Rack entity() {
        Rack entity = new Rack();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRackUnits(12);
        entity.setWidthMm(12);
        entity.setDepthMm(12);
        entity.setMaxWeightKg(new BigDecimal("14.50"));
        entity.setRowLabel("rowLabel-2");
        entity.setPosition("position-2");
        entity.setLabels("labels-2");
        entity.setLifecycleStage(RackLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("RACK-0002");
        entity.setName("Rack two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RackResponse response() {
        RackResponse response = new RackResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RACK-0001");
        response.setName("Rack one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RackSearchCriteria criteria() {
        RackSearchCriteria criteria = new RackSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
