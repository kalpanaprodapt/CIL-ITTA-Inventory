package com.uniti.cil.itta.inventory.circuit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitMapperTest {

    private final CircuitMapper mapper = new CircuitMapper();

    @Test
    void toEntityCopiesEveryField() {
        CircuitCreateRequest request = CircuitFixtures.createRequest();
        Circuit entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCircuitId()).isEqualTo(request.getCircuitId());
        assertThat(entity.getCircuitType()).isEqualTo(request.getCircuitType());
        assertThat(entity.getBandwidthMbps()).isEqualTo(request.getBandwidthMbps());
        assertThat(entity.getProviderId()).isEqualTo(request.getProviderId());
        assertThat(entity.getASiteId()).isEqualTo(request.getASiteId());
        assertThat(entity.getZSiteId()).isEqualTo(request.getZSiteId());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        Circuit entity = CircuitFixtures.entity();
        CircuitResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCircuitId()).isEqualTo(entity.getCircuitId());
        assertThat(response.getCircuitType()).isEqualTo(entity.getCircuitType());
        assertThat(response.getBandwidthMbps()).isEqualTo(entity.getBandwidthMbps());
        assertThat(response.getProviderId()).isEqualTo(entity.getProviderId());
        assertThat(response.getASiteId()).isEqualTo(entity.getASiteId());
        assertThat(response.getZSiteId()).isEqualTo(entity.getZSiteId());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        Circuit entity = CircuitFixtures.entity();
        CircuitSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CircuitFixtures.entity())).hasSameSizeAs(CircuitMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Circuit entity = CircuitFixtures.entity();
        CircuitUpdateRequest request = new CircuitUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCircuitId("circuitId-3");
        request.setCircuitType(CircuitCircuitType.MPLS);
        request.setBandwidthMbps(1003L);
        request.setProviderId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setASiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLabels("labels-3");
        request.setLifecycleStage(CircuitLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCircuitId()).isEqualTo("circuitId-3");
        assertThat(entity.getCircuitType()).isEqualTo(CircuitCircuitType.MPLS);
        assertThat(entity.getBandwidthMbps()).isEqualTo(1003L);
        assertThat(entity.getProviderId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getASiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CircuitLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Circuit entity = CircuitFixtures.entity();
        Circuit before = CircuitFixtures.entity();
        mapper.applyUpdate(new CircuitUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
