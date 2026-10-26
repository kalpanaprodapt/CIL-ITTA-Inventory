package com.uniti.cil.itta.inventory.radio.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.AntennaFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AntennaMapperTest {

    private final AntennaMapper mapper = new AntennaMapper();

    @Test
    void toEntityCopiesEveryField() {
        AntennaCreateRequest request = AntennaFixtures.createRequest();
        Antenna entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSectorId()).isEqualTo(request.getSectorId());
        assertThat(entity.getModel()).isEqualTo(request.getModel());
        assertThat(entity.getHeightM()).isEqualTo(request.getHeightM());
        assertThat(entity.getAzimuthDeg()).isEqualTo(request.getAzimuthDeg());
        assertThat(entity.getTiltDeg()).isEqualTo(request.getTiltDeg());
        assertThat(entity.getGainDbi()).isEqualTo(request.getGainDbi());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        Antenna entity = AntennaFixtures.entity();
        AntennaResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSectorId()).isEqualTo(entity.getSectorId());
        assertThat(response.getModel()).isEqualTo(entity.getModel());
        assertThat(response.getHeightM()).isEqualTo(entity.getHeightM());
        assertThat(response.getAzimuthDeg()).isEqualTo(entity.getAzimuthDeg());
        assertThat(response.getTiltDeg()).isEqualTo(entity.getTiltDeg());
        assertThat(response.getGainDbi()).isEqualTo(entity.getGainDbi());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        Antenna entity = AntennaFixtures.entity();
        AntennaSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AntennaFixtures.entity())).hasSameSizeAs(AntennaMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Antenna entity = AntennaFixtures.entity();
        AntennaUpdateRequest request = new AntennaUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSectorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setModel("model-3");
        request.setHeightM(new BigDecimal("15.50"));
        request.setAzimuthDeg(new BigDecimal("15.50"));
        request.setTiltDeg(new BigDecimal("15.50"));
        request.setGainDbi(new BigDecimal("15.50"));
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(AntennaCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSectorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getModel()).isEqualTo("model-3");
        assertThat(entity.getHeightM()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getAzimuthDeg()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getTiltDeg()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getGainDbi()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(AntennaCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Antenna entity = AntennaFixtures.entity();
        Antenna before = AntennaFixtures.entity();
        mapper.applyUpdate(new AntennaUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
