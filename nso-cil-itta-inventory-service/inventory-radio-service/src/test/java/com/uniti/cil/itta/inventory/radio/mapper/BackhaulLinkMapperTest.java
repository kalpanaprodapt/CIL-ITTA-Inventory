package com.uniti.cil.itta.inventory.radio.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.BackhaulLinkFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BackhaulLinkMapperTest {

    private final BackhaulLinkMapper mapper = new BackhaulLinkMapper();

    @Test
    void toEntityCopiesEveryField() {
        BackhaulLinkCreateRequest request = BackhaulLinkFixtures.createRequest();
        BackhaulLink entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getBaseStationId()).isEqualTo(request.getBaseStationId());
        assertThat(entity.getLinkMedium()).isEqualTo(request.getLinkMedium());
        assertThat(entity.getCapacityMbps()).isEqualTo(request.getCapacityMbps());
        assertThat(entity.getLatencyMs()).isEqualTo(request.getLatencyMs());
        assertThat(entity.getCircuitRef()).isEqualTo(request.getCircuitRef());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        BackhaulLinkResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getBaseStationId()).isEqualTo(entity.getBaseStationId());
        assertThat(response.getLinkMedium()).isEqualTo(entity.getLinkMedium());
        assertThat(response.getCapacityMbps()).isEqualTo(entity.getCapacityMbps());
        assertThat(response.getLatencyMs()).isEqualTo(entity.getLatencyMs());
        assertThat(response.getCircuitRef()).isEqualTo(entity.getCircuitRef());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        BackhaulLinkSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(BackhaulLinkFixtures.entity())).hasSameSizeAs(BackhaulLinkMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        BackhaulLinkUpdateRequest request = new BackhaulLinkUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLinkMedium(BackhaulLinkLinkMedium.SATELLITE);
        request.setCapacityMbps(1003L);
        request.setLatencyMs(new BigDecimal("15.50"));
        request.setCircuitRef("circuitRef-3");
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
        assertThat(entity.getBaseStationId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLinkMedium()).isEqualTo(BackhaulLinkLinkMedium.SATELLITE);
        assertThat(entity.getCapacityMbps()).isEqualTo(1003L);
        assertThat(entity.getLatencyMs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCircuitRef()).isEqualTo("circuitRef-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        BackhaulLink entity = BackhaulLinkFixtures.entity();
        BackhaulLink before = BackhaulLinkFixtures.entity();
        mapper.applyUpdate(new BackhaulLinkUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
