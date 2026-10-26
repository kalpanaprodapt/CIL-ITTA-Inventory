package com.uniti.cil.itta.inventory.facility.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import com.uniti.cil.itta.inventory.facility.support.FloorFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FloorMapperTest {

    private final FloorMapper mapper = new FloorMapper();

    @Test
    void toEntityCopiesEveryField() {
        FloorCreateRequest request = FloorFixtures.createRequest();
        Floor entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getBuildingId()).isEqualTo(request.getBuildingId());
        assertThat(entity.getLevel()).isEqualTo(request.getLevel());
        assertThat(entity.getAreaSqm()).isEqualTo(request.getAreaSqm());
        assertThat(entity.getLoadCapacityKg()).isEqualTo(request.getLoadCapacityKg());
        assertThat(entity.getHasRaisedFloor()).isEqualTo(request.getHasRaisedFloor());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        Floor entity = FloorFixtures.entity();
        FloorResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getBuildingId()).isEqualTo(entity.getBuildingId());
        assertThat(response.getLevel()).isEqualTo(entity.getLevel());
        assertThat(response.getAreaSqm()).isEqualTo(entity.getAreaSqm());
        assertThat(response.getLoadCapacityKg()).isEqualTo(entity.getLoadCapacityKg());
        assertThat(response.getHasRaisedFloor()).isEqualTo(entity.getHasRaisedFloor());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        Floor entity = FloorFixtures.entity();
        FloorSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(FloorFixtures.entity())).hasSameSizeAs(FloorMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Floor entity = FloorFixtures.entity();
        FloorUpdateRequest request = new FloorUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setBuildingId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLevel(13);
        request.setAreaSqm(new BigDecimal("15.50"));
        request.setLoadCapacityKg(new BigDecimal("15.50"));
        request.setHasRaisedFloor(Boolean.TRUE);
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
        assertThat(entity.getBuildingId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLevel()).isEqualTo(13);
        assertThat(entity.getAreaSqm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLoadCapacityKg()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getHasRaisedFloor()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Floor entity = FloorFixtures.entity();
        Floor before = FloorFixtures.entity();
        mapper.applyUpdate(new FloorUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
