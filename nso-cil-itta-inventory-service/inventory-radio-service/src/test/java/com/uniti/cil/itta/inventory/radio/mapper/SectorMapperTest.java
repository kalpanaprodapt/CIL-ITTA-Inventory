package com.uniti.cil.itta.inventory.radio.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.SectorFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SectorMapperTest {

    private final SectorMapper mapper = new SectorMapper();

    @Test
    void toEntityCopiesEveryField() {
        SectorCreateRequest request = SectorFixtures.createRequest();
        Sector entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getBaseStationId()).isEqualTo(request.getBaseStationId());
        assertThat(entity.getSectorNumber()).isEqualTo(request.getSectorNumber());
        assertThat(entity.getPci()).isEqualTo(request.getPci());
        assertThat(entity.getFrequencyMhz()).isEqualTo(request.getFrequencyMhz());
        assertThat(entity.getBandwidthMhz()).isEqualTo(request.getBandwidthMhz());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        Sector entity = SectorFixtures.entity();
        SectorResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getBaseStationId()).isEqualTo(entity.getBaseStationId());
        assertThat(response.getSectorNumber()).isEqualTo(entity.getSectorNumber());
        assertThat(response.getPci()).isEqualTo(entity.getPci());
        assertThat(response.getFrequencyMhz()).isEqualTo(entity.getFrequencyMhz());
        assertThat(response.getBandwidthMhz()).isEqualTo(entity.getBandwidthMhz());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        Sector entity = SectorFixtures.entity();
        SectorSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SectorFixtures.entity())).hasSameSizeAs(SectorMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Sector entity = SectorFixtures.entity();
        SectorUpdateRequest request = new SectorUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setBaseStationId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSectorNumber(13);
        request.setPci(13);
        request.setFrequencyMhz(13);
        request.setBandwidthMhz(13);
        request.setOwner("owner-3");
        request.setCriticality(SectorCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getBaseStationId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSectorNumber()).isEqualTo(13);
        assertThat(entity.getPci()).isEqualTo(13);
        assertThat(entity.getFrequencyMhz()).isEqualTo(13);
        assertThat(entity.getBandwidthMhz()).isEqualTo(13);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SectorCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Sector entity = SectorFixtures.entity();
        Sector before = SectorFixtures.entity();
        mapper.applyUpdate(new SectorUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
