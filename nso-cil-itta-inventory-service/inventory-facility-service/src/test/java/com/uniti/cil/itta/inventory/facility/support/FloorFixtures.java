package com.uniti.cil.itta.inventory.facility.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Floor. */
public final class FloorFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private FloorFixtures() {
    }

    public static FloorCreateRequest createRequest() {
        FloorCreateRequest request = new FloorCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setBuildingId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLevel(11);
        request.setAreaSqm(new BigDecimal("13.50"));
        request.setLoadCapacityKg(new BigDecimal("13.50"));
        request.setHasRaisedFloor(Boolean.TRUE);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("FLOOR-0001");
        request.setName("Floor one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Floor entity() {
        Floor entity = new Floor();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setBuildingId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLevel(12);
        entity.setAreaSqm(new BigDecimal("14.50"));
        entity.setLoadCapacityKg(new BigDecimal("14.50"));
        entity.setHasRaisedFloor(Boolean.FALSE);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("FLOOR-0002");
        entity.setName("Floor two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static FloorResponse response() {
        FloorResponse response = new FloorResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("FLOOR-0001");
        response.setName("Floor one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static FloorSearchCriteria criteria() {
        FloorSearchCriteria criteria = new FloorSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
