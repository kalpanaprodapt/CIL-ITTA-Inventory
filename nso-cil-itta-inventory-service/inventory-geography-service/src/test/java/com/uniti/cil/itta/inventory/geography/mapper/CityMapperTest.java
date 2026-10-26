package com.uniti.cil.itta.inventory.geography.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import com.uniti.cil.itta.inventory.geography.support.CityFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CityMapperTest {

    private final CityMapper mapper = new CityMapper();

    @Test
    void toEntityCopiesEveryField() {
        CityCreateRequest request = CityFixtures.createRequest();
        City entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRegionId()).isEqualTo(request.getRegionId());
        assertThat(entity.getPostalPrefix()).isEqualTo(request.getPostalPrefix());
        assertThat(entity.getPopulation()).isEqualTo(request.getPopulation());
        assertThat(entity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(entity.getLongitude()).isEqualTo(request.getLongitude());
        assertThat(entity.getMetroArea()).isEqualTo(request.getMetroArea());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        City entity = CityFixtures.entity();
        CityResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRegionId()).isEqualTo(entity.getRegionId());
        assertThat(response.getPostalPrefix()).isEqualTo(entity.getPostalPrefix());
        assertThat(response.getPopulation()).isEqualTo(entity.getPopulation());
        assertThat(response.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(response.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(response.getMetroArea()).isEqualTo(entity.getMetroArea());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        City entity = CityFixtures.entity();
        CitySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CityFixtures.entity())).hasSameSizeAs(CityMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        City entity = CityFixtures.entity();
        CityUpdateRequest request = new CityUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRegionId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPostalPrefix("postalPrefix-3");
        request.setPopulation(1003L);
        request.setLatitude(new BigDecimal("15.50"));
        request.setLongitude(new BigDecimal("15.50"));
        request.setMetroArea("metroArea-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(CityLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRegionId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPostalPrefix()).isEqualTo("postalPrefix-3");
        assertThat(entity.getPopulation()).isEqualTo(1003L);
        assertThat(entity.getLatitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLongitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getMetroArea()).isEqualTo("metroArea-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CityLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        City entity = CityFixtures.entity();
        City before = CityFixtures.entity();
        mapper.applyUpdate(new CityUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
