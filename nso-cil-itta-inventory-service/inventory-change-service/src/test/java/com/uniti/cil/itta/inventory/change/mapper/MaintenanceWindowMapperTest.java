package com.uniti.cil.itta.inventory.change.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.MaintenanceWindowFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaintenanceWindowMapperTest {

    private final MaintenanceWindowMapper mapper = new MaintenanceWindowMapper();

    @Test
    void toEntityCopiesEveryField() {
        MaintenanceWindowCreateRequest request = MaintenanceWindowFixtures.createRequest();
        MaintenanceWindow entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getWindowName()).isEqualTo(request.getWindowName());
        assertThat(entity.getStartsAt()).isEqualTo(request.getStartsAt());
        assertThat(entity.getEndsAt()).isEqualTo(request.getEndsAt());
        assertThat(entity.getScope()).isEqualTo(request.getScope());
        assertThat(entity.getImpactLevel()).isEqualTo(request.getImpactLevel());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        MaintenanceWindowResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getWindowName()).isEqualTo(entity.getWindowName());
        assertThat(response.getStartsAt()).isEqualTo(entity.getStartsAt());
        assertThat(response.getEndsAt()).isEqualTo(entity.getEndsAt());
        assertThat(response.getScope()).isEqualTo(entity.getScope());
        assertThat(response.getImpactLevel()).isEqualTo(entity.getImpactLevel());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        MaintenanceWindowSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(MaintenanceWindowFixtures.entity())).hasSameSizeAs(MaintenanceWindowMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        MaintenanceWindowUpdateRequest request = new MaintenanceWindowUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setWindowName("windowName-3");
        request.setStartsAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setEndsAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setScope("scope-3");
        request.setImpactLevel(MaintenanceWindowImpactLevel.OUTAGE);
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(MaintenanceWindowLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getWindowName()).isEqualTo("windowName-3");
        assertThat(entity.getStartsAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getEndsAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getScope()).isEqualTo("scope-3");
        assertThat(entity.getImpactLevel()).isEqualTo(MaintenanceWindowImpactLevel.OUTAGE);
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(MaintenanceWindowLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        MaintenanceWindow before = MaintenanceWindowFixtures.entity();
        mapper.applyUpdate(new MaintenanceWindowUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
