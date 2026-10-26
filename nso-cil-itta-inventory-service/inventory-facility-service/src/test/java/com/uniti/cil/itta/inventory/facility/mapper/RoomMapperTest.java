package com.uniti.cil.itta.inventory.facility.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import com.uniti.cil.itta.inventory.facility.support.RoomFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoomMapperTest {

    private final RoomMapper mapper = new RoomMapper();

    @Test
    void toEntityCopiesEveryField() {
        RoomCreateRequest request = RoomFixtures.createRequest();
        Room entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getFloorId()).isEqualTo(request.getFloorId());
        assertThat(entity.getRoomType()).isEqualTo(request.getRoomType());
        assertThat(entity.getAreaSqm()).isEqualTo(request.getAreaSqm());
        assertThat(entity.getCoolingTons()).isEqualTo(request.getCoolingTons());
        assertThat(entity.getFireZone()).isEqualTo(request.getFireZone());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        Room entity = RoomFixtures.entity();
        RoomResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getFloorId()).isEqualTo(entity.getFloorId());
        assertThat(response.getRoomType()).isEqualTo(entity.getRoomType());
        assertThat(response.getAreaSqm()).isEqualTo(entity.getAreaSqm());
        assertThat(response.getCoolingTons()).isEqualTo(entity.getCoolingTons());
        assertThat(response.getFireZone()).isEqualTo(entity.getFireZone());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        Room entity = RoomFixtures.entity();
        RoomSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RoomFixtures.entity())).hasSameSizeAs(RoomMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Room entity = RoomFixtures.entity();
        RoomUpdateRequest request = new RoomUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setFloorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRoomType(RoomRoomType.POWER);
        request.setAreaSqm(new BigDecimal("15.50"));
        request.setCoolingTons(new BigDecimal("15.50"));
        request.setFireZone("fireZone-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(RoomLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getFloorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRoomType()).isEqualTo(RoomRoomType.POWER);
        assertThat(entity.getAreaSqm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCoolingTons()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getFireZone()).isEqualTo("fireZone-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(RoomLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Room entity = RoomFixtures.entity();
        Room before = RoomFixtures.entity();
        mapper.applyUpdate(new RoomUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
