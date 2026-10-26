package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import com.uniti.cil.itta.inventory.assetmgmt.support.DepreciationScheduleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DepreciationScheduleMapperTest {

    private final DepreciationScheduleMapper mapper = new DepreciationScheduleMapper();

    @Test
    void toEntityCopiesEveryField() {
        DepreciationScheduleCreateRequest request = DepreciationScheduleFixtures.createRequest();
        DepreciationSchedule entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAssetId()).isEqualTo(request.getAssetId());
        assertThat(entity.getMethod()).isEqualTo(request.getMethod());
        assertThat(entity.getUsefulLifeMonths()).isEqualTo(request.getUsefulLifeMonths());
        assertThat(entity.getSalvageValue()).isEqualTo(request.getSalvageValue());
        assertThat(entity.getStartDate()).isEqualTo(request.getStartDate());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        DepreciationScheduleResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAssetId()).isEqualTo(entity.getAssetId());
        assertThat(response.getMethod()).isEqualTo(entity.getMethod());
        assertThat(response.getUsefulLifeMonths()).isEqualTo(entity.getUsefulLifeMonths());
        assertThat(response.getSalvageValue()).isEqualTo(entity.getSalvageValue());
        assertThat(response.getStartDate()).isEqualTo(entity.getStartDate());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        DepreciationScheduleSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(DepreciationScheduleFixtures.entity())).hasSameSizeAs(DepreciationScheduleMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        DepreciationScheduleUpdateRequest request = new DepreciationScheduleUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setMethod(DepreciationScheduleMethod.UNITS);
        request.setUsefulLifeMonths(13);
        request.setSalvageValue(new BigDecimal("15.50"));
        request.setStartDate(LocalDate.of(2026, 4, 13));
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(DepreciationScheduleLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAssetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getMethod()).isEqualTo(DepreciationScheduleMethod.UNITS);
        assertThat(entity.getUsefulLifeMonths()).isEqualTo(13);
        assertThat(entity.getSalvageValue()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(DepreciationScheduleLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        DepreciationSchedule before = DepreciationScheduleFixtures.entity();
        mapper.applyUpdate(new DepreciationScheduleUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
