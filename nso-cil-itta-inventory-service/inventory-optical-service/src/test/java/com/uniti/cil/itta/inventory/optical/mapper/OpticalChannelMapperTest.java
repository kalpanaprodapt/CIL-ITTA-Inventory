package com.uniti.cil.itta.inventory.optical.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.OpticalChannelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpticalChannelMapperTest {

    private final OpticalChannelMapper mapper = new OpticalChannelMapper();

    @Test
    void toEntityCopiesEveryField() {
        OpticalChannelCreateRequest request = OpticalChannelFixtures.createRequest();
        OpticalChannel entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getWavelengthNm()).isEqualTo(request.getWavelengthNm());
        assertThat(entity.getChannelNumber()).isEqualTo(request.getChannelNumber());
        assertThat(entity.getGridType()).isEqualTo(request.getGridType());
        assertThat(entity.getBitrateGbps()).isEqualTo(request.getBitrateGbps());
        assertThat(entity.getSystemId()).isEqualTo(request.getSystemId());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        OpticalChannelResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getWavelengthNm()).isEqualTo(entity.getWavelengthNm());
        assertThat(response.getChannelNumber()).isEqualTo(entity.getChannelNumber());
        assertThat(response.getGridType()).isEqualTo(entity.getGridType());
        assertThat(response.getBitrateGbps()).isEqualTo(entity.getBitrateGbps());
        assertThat(response.getSystemId()).isEqualTo(entity.getSystemId());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        OpticalChannelSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(OpticalChannelFixtures.entity())).hasSameSizeAs(OpticalChannelMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        OpticalChannelUpdateRequest request = new OpticalChannelUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setWavelengthNm(new BigDecimal("15.50"));
        request.setChannelNumber(13);
        request.setGridType(OpticalChannelGridType.FIXED);
        request.setBitrateGbps(new BigDecimal("15.50"));
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setCriticality(OpticalChannelCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getWavelengthNm()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getChannelNumber()).isEqualTo(13);
        assertThat(entity.getGridType()).isEqualTo(OpticalChannelGridType.FIXED);
        assertThat(entity.getBitrateGbps()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSystemId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getCriticality()).isEqualTo(OpticalChannelCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        OpticalChannel entity = OpticalChannelFixtures.entity();
        OpticalChannel before = OpticalChannelFixtures.entity();
        mapper.applyUpdate(new OpticalChannelUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
