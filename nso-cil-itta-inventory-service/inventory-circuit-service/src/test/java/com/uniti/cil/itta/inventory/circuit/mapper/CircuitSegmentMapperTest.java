package com.uniti.cil.itta.inventory.circuit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitSegmentFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitSegmentMapperTest {

    private final CircuitSegmentMapper mapper = new CircuitSegmentMapper();

    @Test
    void toEntityCopiesEveryField() {
        CircuitSegmentCreateRequest request = CircuitSegmentFixtures.createRequest();
        CircuitSegment entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCircuitId()).isEqualTo(request.getCircuitId());
        assertThat(entity.getSegmentOrder()).isEqualTo(request.getSegmentOrder());
        assertThat(entity.getATerminationId()).isEqualTo(request.getATerminationId());
        assertThat(entity.getZTerminationId()).isEqualTo(request.getZTerminationId());
        assertThat(entity.getLatencyMs()).isEqualTo(request.getLatencyMs());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        CircuitSegmentResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCircuitId()).isEqualTo(entity.getCircuitId());
        assertThat(response.getSegmentOrder()).isEqualTo(entity.getSegmentOrder());
        assertThat(response.getATerminationId()).isEqualTo(entity.getATerminationId());
        assertThat(response.getZTerminationId()).isEqualTo(entity.getZTerminationId());
        assertThat(response.getLatencyMs()).isEqualTo(entity.getLatencyMs());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        CircuitSegmentSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CircuitSegmentFixtures.entity())).hasSameSizeAs(CircuitSegmentMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        CircuitSegmentUpdateRequest request = new CircuitSegmentUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSegmentOrder(13);
        request.setATerminationId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZTerminationId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLatencyMs(new BigDecimal("15.50"));
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(CircuitSegmentCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCircuitId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSegmentOrder()).isEqualTo(13);
        assertThat(entity.getATerminationId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZTerminationId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLatencyMs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(CircuitSegmentCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CircuitSegment entity = CircuitSegmentFixtures.entity();
        CircuitSegment before = CircuitSegmentFixtures.entity();
        mapper.applyUpdate(new CircuitSegmentUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
