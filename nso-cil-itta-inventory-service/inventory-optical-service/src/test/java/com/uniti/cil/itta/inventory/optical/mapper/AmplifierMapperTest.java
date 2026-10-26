package com.uniti.cil.itta.inventory.optical.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.AmplifierFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmplifierMapperTest {

    private final AmplifierMapper mapper = new AmplifierMapper();

    @Test
    void toEntityCopiesEveryField() {
        AmplifierCreateRequest request = AmplifierFixtures.createRequest();
        Amplifier entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSystemId()).isEqualTo(request.getSystemId());
        assertThat(entity.getAmpType()).isEqualTo(request.getAmpType());
        assertThat(entity.getGainDb()).isEqualTo(request.getGainDb());
        assertThat(entity.getOutputPowerDbm()).isEqualTo(request.getOutputPowerDbm());
        assertThat(entity.getNoiseFigureDb()).isEqualTo(request.getNoiseFigureDb());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        Amplifier entity = AmplifierFixtures.entity();
        AmplifierResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSystemId()).isEqualTo(entity.getSystemId());
        assertThat(response.getAmpType()).isEqualTo(entity.getAmpType());
        assertThat(response.getGainDb()).isEqualTo(entity.getGainDb());
        assertThat(response.getOutputPowerDbm()).isEqualTo(entity.getOutputPowerDbm());
        assertThat(response.getNoiseFigureDb()).isEqualTo(entity.getNoiseFigureDb());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        Amplifier entity = AmplifierFixtures.entity();
        AmplifierSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AmplifierFixtures.entity())).hasSameSizeAs(AmplifierMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Amplifier entity = AmplifierFixtures.entity();
        AmplifierUpdateRequest request = new AmplifierUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setAmpType(AmplifierAmpType.HYBRID);
        request.setGainDb(new BigDecimal("15.50"));
        request.setOutputPowerDbm(new BigDecimal("15.50"));
        request.setNoiseFigureDb(new BigDecimal("15.50"));
        request.setLifecycleStage(AmplifierLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(AmplifierCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSystemId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getAmpType()).isEqualTo(AmplifierAmpType.HYBRID);
        assertThat(entity.getGainDb()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getOutputPowerDbm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getNoiseFigureDb()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLifecycleStage()).isEqualTo(AmplifierLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(AmplifierCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Amplifier entity = AmplifierFixtures.entity();
        Amplifier before = AmplifierFixtures.entity();
        mapper.applyUpdate(new AmplifierUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
