package com.uniti.cil.itta.inventory.geography.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import com.uniti.cil.itta.inventory.geography.support.RegionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegionMapperTest {

    private final RegionMapper mapper = new RegionMapper();

    @Test
    void toEntityCopiesEveryField() {
        RegionCreateRequest request = RegionFixtures.createRequest();
        Region entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCountryId()).isEqualTo(request.getCountryId());
        assertThat(entity.getRegionType()).isEqualTo(request.getRegionType());
        assertThat(entity.getPopulation()).isEqualTo(request.getPopulation());
        assertThat(entity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(entity.getLongitude()).isEqualTo(request.getLongitude());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        Region entity = RegionFixtures.entity();
        RegionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCountryId()).isEqualTo(entity.getCountryId());
        assertThat(response.getRegionType()).isEqualTo(entity.getRegionType());
        assertThat(response.getPopulation()).isEqualTo(entity.getPopulation());
        assertThat(response.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(response.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        Region entity = RegionFixtures.entity();
        RegionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RegionFixtures.entity())).hasSameSizeAs(RegionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Region entity = RegionFixtures.entity();
        RegionUpdateRequest request = new RegionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCountryId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRegionType(RegionRegionType.TERRITORY);
        request.setPopulation(1003L);
        request.setLatitude(new BigDecimal("15.50"));
        request.setLongitude(new BigDecimal("15.50"));
        request.setCriticality(RegionCriticality.HIGH);
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
        assertThat(entity.getCountryId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRegionType()).isEqualTo(RegionRegionType.TERRITORY);
        assertThat(entity.getPopulation()).isEqualTo(1003L);
        assertThat(entity.getLatitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLongitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCriticality()).isEqualTo(RegionCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Region entity = RegionFixtures.entity();
        Region before = RegionFixtures.entity();
        mapper.applyUpdate(new RegionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
