package com.uniti.cil.itta.inventory.change.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.ChangeRequestFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChangeRequestMapperTest {

    private final ChangeRequestMapper mapper = new ChangeRequestMapper();

    @Test
    void toEntityCopiesEveryField() {
        ChangeRequestCreateRequest request = ChangeRequestFixtures.createRequest();
        ChangeRequest entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getChangeNumber()).isEqualTo(request.getChangeNumber());
        assertThat(entity.getChangeType()).isEqualTo(request.getChangeType());
        assertThat(entity.getRiskLevel()).isEqualTo(request.getRiskLevel());
        assertThat(entity.getPlannedStart()).isEqualTo(request.getPlannedStart());
        assertThat(entity.getPlannedEnd()).isEqualTo(request.getPlannedEnd());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        ChangeRequestResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getChangeNumber()).isEqualTo(entity.getChangeNumber());
        assertThat(response.getChangeType()).isEqualTo(entity.getChangeType());
        assertThat(response.getRiskLevel()).isEqualTo(entity.getRiskLevel());
        assertThat(response.getPlannedStart()).isEqualTo(entity.getPlannedStart());
        assertThat(response.getPlannedEnd()).isEqualTo(entity.getPlannedEnd());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        ChangeRequestSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ChangeRequestFixtures.entity())).hasSameSizeAs(ChangeRequestMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        ChangeRequestUpdateRequest request = new ChangeRequestUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setChangeNumber("changeNumber-3");
        request.setChangeType(ChangeRequestChangeType.EMERGENCY);
        request.setRiskLevel(ChangeRequestRiskLevel.HIGH);
        request.setPlannedStart(Instant.parse("2026-04-13T10:15:30Z"));
        request.setPlannedEnd(Instant.parse("2026-04-13T10:15:30Z"));
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(ChangeRequestLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getChangeNumber()).isEqualTo("changeNumber-3");
        assertThat(entity.getChangeType()).isEqualTo(ChangeRequestChangeType.EMERGENCY);
        assertThat(entity.getRiskLevel()).isEqualTo(ChangeRequestRiskLevel.HIGH);
        assertThat(entity.getPlannedStart()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getPlannedEnd()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ChangeRequestLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        ChangeRequest before = ChangeRequestFixtures.entity();
        mapper.applyUpdate(new ChangeRequestUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
