package com.uniti.cil.itta.inventory.capacity.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Reservation. */
public final class ReservationFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ReservationFixtures() {
    }

    public static ReservationCreateRequest createRequest() {
        ReservationCreateRequest request = new ReservationCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setResourceRef("resourceRef-1");
        request.setReservedFor("reservedFor-1");
        request.setQuantity(new BigDecimal("13.50"));
        request.setStartDate(LocalDate.of(2026, 2, 11));
        request.setEndDate(LocalDate.of(2026, 2, 11));
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ReservationLifecycleStage.PLAN);
        request.setCode("RESERV-0001");
        request.setName("Reservation one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Reservation entity() {
        Reservation entity = new Reservation();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setResourceRef("resourceRef-2");
        entity.setReservedFor("reservedFor-2");
        entity.setQuantity(new BigDecimal("14.50"));
        entity.setStartDate(LocalDate.of(2026, 3, 12));
        entity.setEndDate(LocalDate.of(2026, 3, 12));
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ReservationLifecycleStage.BUILD);
        entity.setCode("RESERV-0002");
        entity.setName("Reservation two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ReservationResponse response() {
        ReservationResponse response = new ReservationResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RESERV-0001");
        response.setName("Reservation one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ReservationSearchCriteria criteria() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
