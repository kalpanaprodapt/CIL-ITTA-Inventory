package com.uniti.cil.itta.inventory.capacity.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.ForecastModelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ForecastModelMapperTest {

    private final ForecastModelMapper mapper = new ForecastModelMapper();

    @Test
    void toEntityCopiesEveryField() {
        ForecastModelCreateRequest request = ForecastModelFixtures.createRequest();
        ForecastModel entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getModelName()).isEqualTo(request.getModelName());
        assertThat(entity.getAlgorithm()).isEqualTo(request.getAlgorithm());
        assertThat(entity.getTrainedOn()).isEqualTo(request.getTrainedOn());
        assertThat(entity.getMape()).isEqualTo(request.getMape());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        ForecastModel entity = ForecastModelFixtures.entity();
        ForecastModelResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getModelName()).isEqualTo(entity.getModelName());
        assertThat(response.getAlgorithm()).isEqualTo(entity.getAlgorithm());
        assertThat(response.getTrainedOn()).isEqualTo(entity.getTrainedOn());
        assertThat(response.getMape()).isEqualTo(entity.getMape());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        ForecastModel entity = ForecastModelFixtures.entity();
        ForecastModelSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ForecastModelFixtures.entity())).hasSameSizeAs(ForecastModelMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ForecastModel entity = ForecastModelFixtures.entity();
        ForecastModelUpdateRequest request = new ForecastModelUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setModelName("modelName-3");
        request.setAlgorithm(ForecastModelAlgorithm.ARIMA);
        request.setTrainedOn(Instant.parse("2026-04-13T10:15:30Z"));
        request.setMape(new BigDecimal("15.50"));
        request.setCriticality(ForecastModelCriticality.HIGH);
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
        assertThat(entity.getModelName()).isEqualTo("modelName-3");
        assertThat(entity.getAlgorithm()).isEqualTo(ForecastModelAlgorithm.ARIMA);
        assertThat(entity.getTrainedOn()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getMape()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCriticality()).isEqualTo(ForecastModelCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ForecastModel entity = ForecastModelFixtures.entity();
        ForecastModel before = ForecastModelFixtures.entity();
        mapper.applyUpdate(new ForecastModelUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
