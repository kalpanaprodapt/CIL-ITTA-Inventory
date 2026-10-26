package com.uniti.cil.itta.inventory.radio.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.RadioUnitFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RadioUnitMapperTest {

    private final RadioUnitMapper mapper = new RadioUnitMapper();

    @Test
    void toEntityCopiesEveryField() {
        RadioUnitCreateRequest request = RadioUnitFixtures.createRequest();
        RadioUnit entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSiteId()).isEqualTo(request.getSiteId());
        assertThat(entity.getUnitType()).isEqualTo(request.getUnitType());
        assertThat(entity.getBandMhz()).isEqualTo(request.getBandMhz());
        assertThat(entity.getTxPowerDbm()).isEqualTo(request.getTxPowerDbm());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        RadioUnit entity = RadioUnitFixtures.entity();
        RadioUnitResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSiteId()).isEqualTo(entity.getSiteId());
        assertThat(response.getUnitType()).isEqualTo(entity.getUnitType());
        assertThat(response.getBandMhz()).isEqualTo(entity.getBandMhz());
        assertThat(response.getTxPowerDbm()).isEqualTo(entity.getTxPowerDbm());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        RadioUnit entity = RadioUnitFixtures.entity();
        RadioUnitSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RadioUnitFixtures.entity())).hasSameSizeAs(RadioUnitMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        RadioUnit entity = RadioUnitFixtures.entity();
        RadioUnitUpdateRequest request = new RadioUnitUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setUnitType(RadioUnitUnitType.MICROWAVE);
        request.setBandMhz(13);
        request.setTxPowerDbm(new BigDecimal("15.50"));
        request.setSerialNumber("serialNumber-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(RadioUnitLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getUnitType()).isEqualTo(RadioUnitUnitType.MICROWAVE);
        assertThat(entity.getBandMhz()).isEqualTo(13);
        assertThat(entity.getTxPowerDbm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(RadioUnitLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        RadioUnit entity = RadioUnitFixtures.entity();
        RadioUnit before = RadioUnitFixtures.entity();
        mapper.applyUpdate(new RadioUnitUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
