package com.uniti.cil.itta.inventory.radio.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for BackhaulLink. */
public final class BackhaulLinkFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private BackhaulLinkFixtures() {
    }

    public static BackhaulLinkCreateRequest createRequest() {
        BackhaulLinkCreateRequest request = new BackhaulLinkCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLinkMedium(BackhaulLinkLinkMedium.FIBER);
        request.setCapacityMbps(1001L);
        request.setLatencyMs(new BigDecimal("13.50"));
        request.setCircuitRef("circuitRef-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("BACKHA-0001");
        request.setName("Backhaul Link one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static BackhaulLink entity() {
        BackhaulLink entity = new BackhaulLink();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLinkMedium(BackhaulLinkLinkMedium.MICROWAVE);
        entity.setCapacityMbps(1002L);
        entity.setLatencyMs(new BigDecimal("14.50"));
        entity.setCircuitRef("circuitRef-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("BACKHA-0002");
        entity.setName("Backhaul Link two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static BackhaulLinkResponse response() {
        BackhaulLinkResponse response = new BackhaulLinkResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("BACKHA-0001");
        response.setName("Backhaul Link one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static BackhaulLinkSearchCriteria criteria() {
        BackhaulLinkSearchCriteria criteria = new BackhaulLinkSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
