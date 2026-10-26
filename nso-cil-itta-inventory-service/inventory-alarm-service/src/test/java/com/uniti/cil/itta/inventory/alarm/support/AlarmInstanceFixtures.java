package com.uniti.cil.itta.inventory.alarm.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for AlarmInstance. */
public final class AlarmInstanceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AlarmInstanceFixtures() {
    }

    public static AlarmInstanceCreateRequest createRequest() {
        AlarmInstanceCreateRequest request = new AlarmInstanceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setDefinitionId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSourceRef("sourceRef-1");
        request.setRaisedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setClearedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setAckedBy("ackedBy-1");
        request.setAlarmState(AlarmInstanceAlarmState.ACTIVE);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(AlarmInstanceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("ALARMI-0001");
        request.setName("Alarm Instance one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static AlarmInstance entity() {
        AlarmInstance entity = new AlarmInstance();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setDefinitionId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSourceRef("sourceRef-2");
        entity.setRaisedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setClearedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setAckedBy("ackedBy-2");
        entity.setAlarmState(AlarmInstanceAlarmState.ACKED);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(AlarmInstanceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("ALARMI-0002");
        entity.setName("Alarm Instance two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AlarmInstanceResponse response() {
        AlarmInstanceResponse response = new AlarmInstanceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ALARMI-0001");
        response.setName("Alarm Instance one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AlarmInstanceSearchCriteria criteria() {
        AlarmInstanceSearchCriteria criteria = new AlarmInstanceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
