package com.uniti.cil.itta.inventory.alarm.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.AlarmDefinitionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmDefinitionMapperTest {

    private final AlarmDefinitionMapper mapper = new AlarmDefinitionMapper();

    @Test
    void toEntityCopiesEveryField() {
        AlarmDefinitionCreateRequest request = AlarmDefinitionFixtures.createRequest();
        AlarmDefinition entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAlarmCode()).isEqualTo(request.getAlarmCode());
        assertThat(entity.getSeverity()).isEqualTo(request.getSeverity());
        assertThat(entity.getProbableCause()).isEqualTo(request.getProbableCause());
        assertThat(entity.getAutoClear()).isEqualTo(request.getAutoClear());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        AlarmDefinitionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAlarmCode()).isEqualTo(entity.getAlarmCode());
        assertThat(response.getSeverity()).isEqualTo(entity.getSeverity());
        assertThat(response.getProbableCause()).isEqualTo(entity.getProbableCause());
        assertThat(response.getAutoClear()).isEqualTo(entity.getAutoClear());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        AlarmDefinitionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AlarmDefinitionFixtures.entity())).hasSameSizeAs(AlarmDefinitionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        AlarmDefinitionUpdateRequest request = new AlarmDefinitionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAlarmCode("alarmCode-3");
        request.setSeverity(AlarmDefinitionSeverity.MINOR);
        request.setProbableCause("probableCause-3");
        request.setAutoClear(Boolean.TRUE);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAlarmCode()).isEqualTo("alarmCode-3");
        assertThat(entity.getSeverity()).isEqualTo(AlarmDefinitionSeverity.MINOR);
        assertThat(entity.getProbableCause()).isEqualTo("probableCause-3");
        assertThat(entity.getAutoClear()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        AlarmDefinition before = AlarmDefinitionFixtures.entity();
        mapper.applyUpdate(new AlarmDefinitionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
