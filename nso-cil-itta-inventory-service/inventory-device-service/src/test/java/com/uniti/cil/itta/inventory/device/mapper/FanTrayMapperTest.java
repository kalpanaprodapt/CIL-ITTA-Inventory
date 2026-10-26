package com.uniti.cil.itta.inventory.device.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.FanTrayFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FanTrayMapperTest {

    private final FanTrayMapper mapper = new FanTrayMapper();

    @Test
    void toEntityCopiesEveryField() {
        FanTrayCreateRequest request = FanTrayFixtures.createRequest();
        FanTray entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getChassisId()).isEqualTo(request.getChassisId());
        assertThat(entity.getSlotNumber()).isEqualTo(request.getSlotNumber());
        assertThat(entity.getFanCount()).isEqualTo(request.getFanCount());
        assertThat(entity.getAirflow()).isEqualTo(request.getAirflow());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        FanTray entity = FanTrayFixtures.entity();
        FanTrayResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getChassisId()).isEqualTo(entity.getChassisId());
        assertThat(response.getSlotNumber()).isEqualTo(entity.getSlotNumber());
        assertThat(response.getFanCount()).isEqualTo(entity.getFanCount());
        assertThat(response.getAirflow()).isEqualTo(entity.getAirflow());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        FanTray entity = FanTrayFixtures.entity();
        FanTraySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(FanTrayFixtures.entity())).hasSameSizeAs(FanTrayMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        FanTray entity = FanTrayFixtures.entity();
        FanTrayUpdateRequest request = new FanTrayUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSlotNumber(13);
        request.setFanCount(13);
        request.setAirflow(FanTrayAirflow.FRONT_TO_BACK);
        request.setSerialNumber("serialNumber-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(FanTrayLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getChassisId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSlotNumber()).isEqualTo(13);
        assertThat(entity.getFanCount()).isEqualTo(13);
        assertThat(entity.getAirflow()).isEqualTo(FanTrayAirflow.FRONT_TO_BACK);
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(FanTrayLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        FanTray entity = FanTrayFixtures.entity();
        FanTray before = FanTrayFixtures.entity();
        mapper.applyUpdate(new FanTrayUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
