package com.uniti.cil.itta.inventory.circuit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitTerminationFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitTerminationMapperTest {

    private final CircuitTerminationMapper mapper = new CircuitTerminationMapper();

    @Test
    void toEntityCopiesEveryField() {
        CircuitTerminationCreateRequest request = CircuitTerminationFixtures.createRequest();
        CircuitTermination entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCircuitId()).isEqualTo(request.getCircuitId());
        assertThat(entity.getSide()).isEqualTo(request.getSide());
        assertThat(entity.getPortId()).isEqualTo(request.getPortId());
        assertThat(entity.getXconnectId()).isEqualTo(request.getXconnectId());
        assertThat(entity.getPatchInfo()).isEqualTo(request.getPatchInfo());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        CircuitTerminationResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCircuitId()).isEqualTo(entity.getCircuitId());
        assertThat(response.getSide()).isEqualTo(entity.getSide());
        assertThat(response.getPortId()).isEqualTo(entity.getPortId());
        assertThat(response.getXconnectId()).isEqualTo(entity.getXconnectId());
        assertThat(response.getPatchInfo()).isEqualTo(entity.getPatchInfo());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        CircuitTerminationSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CircuitTerminationFixtures.entity())).hasSameSizeAs(CircuitTerminationMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        CircuitTerminationUpdateRequest request = new CircuitTerminationUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSide(CircuitTerminationSide.A);
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setXconnectId("xconnectId-3");
        request.setPatchInfo("patchInfo-3");
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCircuitId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSide()).isEqualTo(CircuitTerminationSide.A);
        assertThat(entity.getPortId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getXconnectId()).isEqualTo("xconnectId-3");
        assertThat(entity.getPatchInfo()).isEqualTo("patchInfo-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CircuitTermination entity = CircuitTerminationFixtures.entity();
        CircuitTermination before = CircuitTerminationFixtures.entity();
        mapper.applyUpdate(new CircuitTerminationUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
