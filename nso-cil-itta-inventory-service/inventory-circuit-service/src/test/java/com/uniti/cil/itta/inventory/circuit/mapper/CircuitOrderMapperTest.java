package com.uniti.cil.itta.inventory.circuit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitOrderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitOrderMapperTest {

    private final CircuitOrderMapper mapper = new CircuitOrderMapper();

    @Test
    void toEntityCopiesEveryField() {
        CircuitOrderCreateRequest request = CircuitOrderFixtures.createRequest();
        CircuitOrder entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCircuitId()).isEqualTo(request.getCircuitId());
        assertThat(entity.getOrderNumber()).isEqualTo(request.getOrderNumber());
        assertThat(entity.getOrderedOn()).isEqualTo(request.getOrderedOn());
        assertThat(entity.getRequestedDate()).isEqualTo(request.getRequestedDate());
        assertThat(entity.getCommittedDate()).isEqualTo(request.getCommittedDate());
        assertThat(entity.getOrderStatus()).isEqualTo(request.getOrderStatus());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        CircuitOrderResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCircuitId()).isEqualTo(entity.getCircuitId());
        assertThat(response.getOrderNumber()).isEqualTo(entity.getOrderNumber());
        assertThat(response.getOrderedOn()).isEqualTo(entity.getOrderedOn());
        assertThat(response.getRequestedDate()).isEqualTo(entity.getRequestedDate());
        assertThat(response.getCommittedDate()).isEqualTo(entity.getCommittedDate());
        assertThat(response.getOrderStatus()).isEqualTo(entity.getOrderStatus());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        CircuitOrderSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CircuitOrderFixtures.entity())).hasSameSizeAs(CircuitOrderMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        CircuitOrderUpdateRequest request = new CircuitOrderUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setOrderNumber("orderNumber-3");
        request.setOrderedOn(LocalDate.of(2026, 4, 13));
        request.setRequestedDate(LocalDate.of(2026, 4, 13));
        request.setCommittedDate(LocalDate.of(2026, 4, 13));
        request.setOrderStatus(CircuitOrderOrderStatus.CONFIRMED);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(CircuitOrderLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCircuitId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getOrderNumber()).isEqualTo("orderNumber-3");
        assertThat(entity.getOrderedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getRequestedDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getCommittedDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getOrderStatus()).isEqualTo(CircuitOrderOrderStatus.CONFIRMED);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CircuitOrderLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CircuitOrder entity = CircuitOrderFixtures.entity();
        CircuitOrder before = CircuitOrderFixtures.entity();
        mapper.applyUpdate(new CircuitOrderUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
