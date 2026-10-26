package com.uniti.cil.itta.inventory.facility.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Room. */
public final class RoomFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RoomFixtures() {
    }

    public static RoomCreateRequest createRequest() {
        RoomCreateRequest request = new RoomCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setFloorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRoomType(RoomRoomType.MMR);
        request.setAreaSqm(new BigDecimal("13.50"));
        request.setCoolingTons(new BigDecimal("13.50"));
        request.setFireZone("fireZone-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(RoomLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("ROOM-0001");
        request.setName("Room one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Room entity() {
        Room entity = new Room();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setFloorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRoomType(RoomRoomType.COLO);
        entity.setAreaSqm(new BigDecimal("14.50"));
        entity.setCoolingTons(new BigDecimal("14.50"));
        entity.setFireZone("fireZone-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(RoomLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("ROOM-0002");
        entity.setName("Room two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RoomResponse response() {
        RoomResponse response = new RoomResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ROOM-0001");
        response.setName("Room one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RoomSearchCriteria criteria() {
        RoomSearchCriteria criteria = new RoomSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
