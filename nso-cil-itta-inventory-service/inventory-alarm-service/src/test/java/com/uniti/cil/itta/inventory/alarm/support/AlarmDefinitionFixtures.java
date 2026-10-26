package com.uniti.cil.itta.inventory.alarm.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for AlarmDefinition. */
public final class AlarmDefinitionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AlarmDefinitionFixtures() {
    }

    public static AlarmDefinitionCreateRequest createRequest() {
        AlarmDefinitionCreateRequest request = new AlarmDefinitionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAlarmCode("alarmCode-1");
        request.setSeverity(AlarmDefinitionSeverity.INDETERMINATE);
        request.setProbableCause("probableCause-1");
        request.setAutoClear(Boolean.TRUE);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("ALARMD-0001");
        request.setName("Alarm Definition one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static AlarmDefinition entity() {
        AlarmDefinition entity = new AlarmDefinition();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAlarmCode("alarmCode-2");
        entity.setSeverity(AlarmDefinitionSeverity.WARNING);
        entity.setProbableCause("probableCause-2");
        entity.setAutoClear(Boolean.FALSE);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("ALARMD-0002");
        entity.setName("Alarm Definition two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AlarmDefinitionResponse response() {
        AlarmDefinitionResponse response = new AlarmDefinitionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ALARMD-0001");
        response.setName("Alarm Definition one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AlarmDefinitionSearchCriteria criteria() {
        AlarmDefinitionSearchCriteria criteria = new AlarmDefinitionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
