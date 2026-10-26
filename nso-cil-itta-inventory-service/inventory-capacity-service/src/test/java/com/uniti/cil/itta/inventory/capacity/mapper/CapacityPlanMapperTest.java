package com.uniti.cil.itta.inventory.capacity.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.CapacityPlanFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CapacityPlanMapperTest {

    private final CapacityPlanMapper mapper = new CapacityPlanMapper();

    @Test
    void toEntityCopiesEveryField() {
        CapacityPlanCreateRequest request = CapacityPlanFixtures.createRequest();
        CapacityPlan entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPlanName()).isEqualTo(request.getPlanName());
        assertThat(entity.getHorizonMonths()).isEqualTo(request.getHorizonMonths());
        assertThat(entity.getTargetUtilizationPct()).isEqualTo(request.getTargetUtilizationPct());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getApprovedOn()).isEqualTo(request.getApprovedOn());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        CapacityPlanResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPlanName()).isEqualTo(entity.getPlanName());
        assertThat(response.getHorizonMonths()).isEqualTo(entity.getHorizonMonths());
        assertThat(response.getTargetUtilizationPct()).isEqualTo(entity.getTargetUtilizationPct());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getApprovedOn()).isEqualTo(entity.getApprovedOn());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        CapacityPlanSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CapacityPlanFixtures.entity())).hasSameSizeAs(CapacityPlanMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        CapacityPlanUpdateRequest request = new CapacityPlanUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPlanName("planName-3");
        request.setHorizonMonths(13);
        request.setTargetUtilizationPct(new BigDecimal("15.50"));
        request.setOwner("owner-3");
        request.setApprovedOn(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(CapacityPlanLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPlanName()).isEqualTo("planName-3");
        assertThat(entity.getHorizonMonths()).isEqualTo(13);
        assertThat(entity.getTargetUtilizationPct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getApprovedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CapacityPlanLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CapacityPlan entity = CapacityPlanFixtures.entity();
        CapacityPlan before = CapacityPlanFixtures.entity();
        mapper.applyUpdate(new CapacityPlanUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
