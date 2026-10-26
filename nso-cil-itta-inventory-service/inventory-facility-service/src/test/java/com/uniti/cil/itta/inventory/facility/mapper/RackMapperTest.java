package com.uniti.cil.itta.inventory.facility.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import com.uniti.cil.itta.inventory.facility.support.RackFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RackMapperTest {

    private final RackMapper mapper = new RackMapper();

    @Test
    void toEntityCopiesEveryField() {
        RackCreateRequest request = RackFixtures.createRequest();
        Rack entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRoomId()).isEqualTo(request.getRoomId());
        assertThat(entity.getRackUnits()).isEqualTo(request.getRackUnits());
        assertThat(entity.getWidthMm()).isEqualTo(request.getWidthMm());
        assertThat(entity.getDepthMm()).isEqualTo(request.getDepthMm());
        assertThat(entity.getMaxWeightKg()).isEqualTo(request.getMaxWeightKg());
        assertThat(entity.getRowLabel()).isEqualTo(request.getRowLabel());
        assertThat(entity.getPosition()).isEqualTo(request.getPosition());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        Rack entity = RackFixtures.entity();
        RackResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRoomId()).isEqualTo(entity.getRoomId());
        assertThat(response.getRackUnits()).isEqualTo(entity.getRackUnits());
        assertThat(response.getWidthMm()).isEqualTo(entity.getWidthMm());
        assertThat(response.getDepthMm()).isEqualTo(entity.getDepthMm());
        assertThat(response.getMaxWeightKg()).isEqualTo(entity.getMaxWeightKg());
        assertThat(response.getRowLabel()).isEqualTo(entity.getRowLabel());
        assertThat(response.getPosition()).isEqualTo(entity.getPosition());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        Rack entity = RackFixtures.entity();
        RackSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RackFixtures.entity())).hasSameSizeAs(RackMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Rack entity = RackFixtures.entity();
        RackUpdateRequest request = new RackUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRackUnits(13);
        request.setWidthMm(13);
        request.setDepthMm(13);
        request.setMaxWeightKg(new BigDecimal("15.50"));
        request.setRowLabel("rowLabel-3");
        request.setPosition("position-3");
        request.setLabels("labels-3");
        request.setLifecycleStage(RackLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRoomId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRackUnits()).isEqualTo(13);
        assertThat(entity.getWidthMm()).isEqualTo(13);
        assertThat(entity.getDepthMm()).isEqualTo(13);
        assertThat(entity.getMaxWeightKg()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getRowLabel()).isEqualTo("rowLabel-3");
        assertThat(entity.getPosition()).isEqualTo("position-3");
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(RackLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Rack entity = RackFixtures.entity();
        Rack before = RackFixtures.entity();
        mapper.applyUpdate(new RackUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
