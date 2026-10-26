package com.uniti.cil.itta.inventory.change.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for FieldTask. */
public final class FieldTaskFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private FieldTaskFixtures() {
    }

    public static FieldTaskCreateRequest createRequest() {
        FieldTaskCreateRequest request = new FieldTaskCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setWorkOrderId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTaskName("taskName-1");
        request.setSequence(11);
        request.setDurationMinutes(11);
        request.setCompletedOn(Instant.parse("2026-02-11T10:15:30Z"));
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(FieldTaskCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("FIELDT-0001");
        request.setName("Field Task one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static FieldTask entity() {
        FieldTask entity = new FieldTask();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setWorkOrderId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTaskName("taskName-2");
        entity.setSequence(12);
        entity.setDurationMinutes(12);
        entity.setCompletedOn(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(FieldTaskCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("FIELDT-0002");
        entity.setName("Field Task two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static FieldTaskResponse response() {
        FieldTaskResponse response = new FieldTaskResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("FIELDT-0001");
        response.setName("Field Task one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static FieldTaskSearchCriteria criteria() {
        FieldTaskSearchCriteria criteria = new FieldTaskSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
