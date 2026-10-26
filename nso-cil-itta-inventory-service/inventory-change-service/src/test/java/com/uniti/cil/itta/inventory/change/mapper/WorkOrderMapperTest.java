package com.uniti.cil.itta.inventory.change.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.WorkOrderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WorkOrderMapperTest {

    private final WorkOrderMapper mapper = new WorkOrderMapper();

    @Test
    void toEntityCopiesEveryField() {
        WorkOrderCreateRequest request = WorkOrderFixtures.createRequest();
        WorkOrder entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getWorkOrderNumber()).isEqualTo(request.getWorkOrderNumber());
        assertThat(entity.getWorkType()).isEqualTo(request.getWorkType());
        assertThat(entity.getAssignedTechnicianId()).isEqualTo(request.getAssignedTechnicianId());
        assertThat(entity.getDueDate()).isEqualTo(request.getDueDate());
        assertThat(entity.getWorkState()).isEqualTo(request.getWorkState());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        WorkOrder entity = WorkOrderFixtures.entity();
        WorkOrderResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getWorkOrderNumber()).isEqualTo(entity.getWorkOrderNumber());
        assertThat(response.getWorkType()).isEqualTo(entity.getWorkType());
        assertThat(response.getAssignedTechnicianId()).isEqualTo(entity.getAssignedTechnicianId());
        assertThat(response.getDueDate()).isEqualTo(entity.getDueDate());
        assertThat(response.getWorkState()).isEqualTo(entity.getWorkState());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        WorkOrder entity = WorkOrderFixtures.entity();
        WorkOrderSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(WorkOrderFixtures.entity())).hasSameSizeAs(WorkOrderMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        WorkOrder entity = WorkOrderFixtures.entity();
        WorkOrderUpdateRequest request = new WorkOrderUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setWorkOrderNumber("workOrderNumber-3");
        request.setWorkType("workType-3");
        request.setAssignedTechnicianId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setDueDate(LocalDate.of(2026, 4, 13));
        request.setWorkState(WorkOrderWorkState.IN_PROGRESS);
        request.setLifecycleStage(WorkOrderLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(WorkOrderCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getWorkOrderNumber()).isEqualTo("workOrderNumber-3");
        assertThat(entity.getWorkType()).isEqualTo("workType-3");
        assertThat(entity.getAssignedTechnicianId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getDueDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getWorkState()).isEqualTo(WorkOrderWorkState.IN_PROGRESS);
        assertThat(entity.getLifecycleStage()).isEqualTo(WorkOrderLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(WorkOrderCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        WorkOrder entity = WorkOrderFixtures.entity();
        WorkOrder before = WorkOrderFixtures.entity();
        mapper.applyUpdate(new WorkOrderUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
