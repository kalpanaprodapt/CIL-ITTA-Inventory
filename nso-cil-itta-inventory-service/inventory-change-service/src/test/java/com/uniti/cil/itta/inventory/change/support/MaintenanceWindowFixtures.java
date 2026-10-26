package com.uniti.cil.itta.inventory.change.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for MaintenanceWindow. */
public final class MaintenanceWindowFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private MaintenanceWindowFixtures() {
    }

    public static MaintenanceWindowCreateRequest createRequest() {
        MaintenanceWindowCreateRequest request = new MaintenanceWindowCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setWindowName("windowName-1");
        request.setStartsAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setEndsAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setScope("scope-1");
        request.setImpactLevel(MaintenanceWindowImpactLevel.NONE);
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(MaintenanceWindowLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("MAINTE-0001");
        request.setName("Maintenance Window one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static MaintenanceWindow entity() {
        MaintenanceWindow entity = new MaintenanceWindow();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setWindowName("windowName-2");
        entity.setStartsAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setEndsAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setScope("scope-2");
        entity.setImpactLevel(MaintenanceWindowImpactLevel.DEGRADED);
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(MaintenanceWindowLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("MAINTE-0002");
        entity.setName("Maintenance Window two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static MaintenanceWindowResponse response() {
        MaintenanceWindowResponse response = new MaintenanceWindowResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("MAINTE-0001");
        response.setName("Maintenance Window one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static MaintenanceWindowSearchCriteria criteria() {
        MaintenanceWindowSearchCriteria criteria = new MaintenanceWindowSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
