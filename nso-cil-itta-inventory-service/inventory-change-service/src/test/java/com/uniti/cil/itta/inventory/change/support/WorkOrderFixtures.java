package com.uniti.cil.itta.inventory.change.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for WorkOrder. */
public final class WorkOrderFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private WorkOrderFixtures() {
    }

    public static WorkOrderCreateRequest createRequest() {
        WorkOrderCreateRequest request = new WorkOrderCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setWorkOrderNumber("workOrderNumber-1");
        request.setWorkType("workType-1");
        request.setAssignedTechnicianId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setDueDate(LocalDate.of(2026, 2, 11));
        request.setWorkState(WorkOrderWorkState.OPEN);
        request.setLifecycleStage(WorkOrderLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(WorkOrderCriticality.LOW);
        request.setCode("WORKOR-0001");
        request.setName("Work Order one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static WorkOrder entity() {
        WorkOrder entity = new WorkOrder();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setWorkOrderNumber("workOrderNumber-2");
        entity.setWorkType("workType-2");
        entity.setAssignedTechnicianId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setDueDate(LocalDate.of(2026, 3, 12));
        entity.setWorkState(WorkOrderWorkState.ASSIGNED);
        entity.setLifecycleStage(WorkOrderLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(WorkOrderCriticality.MEDIUM);
        entity.setCode("WORKOR-0002");
        entity.setName("Work Order two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static WorkOrderResponse response() {
        WorkOrderResponse response = new WorkOrderResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("WORKOR-0001");
        response.setName("Work Order one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static WorkOrderSearchCriteria criteria() {
        WorkOrderSearchCriteria criteria = new WorkOrderSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
