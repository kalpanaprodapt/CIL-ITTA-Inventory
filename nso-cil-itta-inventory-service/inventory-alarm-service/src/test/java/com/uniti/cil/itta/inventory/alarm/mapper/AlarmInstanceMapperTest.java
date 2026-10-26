package com.uniti.cil.itta.inventory.alarm.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.AlarmInstanceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmInstanceMapperTest {

    private final AlarmInstanceMapper mapper = new AlarmInstanceMapper();

    @Test
    void toEntityCopiesEveryField() {
        AlarmInstanceCreateRequest request = AlarmInstanceFixtures.createRequest();
        AlarmInstance entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getDefinitionId()).isEqualTo(request.getDefinitionId());
        assertThat(entity.getSourceRef()).isEqualTo(request.getSourceRef());
        assertThat(entity.getRaisedAt()).isEqualTo(request.getRaisedAt());
        assertThat(entity.getClearedAt()).isEqualTo(request.getClearedAt());
        assertThat(entity.getAckedBy()).isEqualTo(request.getAckedBy());
        assertThat(entity.getAlarmState()).isEqualTo(request.getAlarmState());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        AlarmInstanceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getDefinitionId()).isEqualTo(entity.getDefinitionId());
        assertThat(response.getSourceRef()).isEqualTo(entity.getSourceRef());
        assertThat(response.getRaisedAt()).isEqualTo(entity.getRaisedAt());
        assertThat(response.getClearedAt()).isEqualTo(entity.getClearedAt());
        assertThat(response.getAckedBy()).isEqualTo(entity.getAckedBy());
        assertThat(response.getAlarmState()).isEqualTo(entity.getAlarmState());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        AlarmInstanceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AlarmInstanceFixtures.entity())).hasSameSizeAs(AlarmInstanceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        AlarmInstanceUpdateRequest request = new AlarmInstanceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setDefinitionId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSourceRef("sourceRef-3");
        request.setRaisedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setClearedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setAckedBy("ackedBy-3");
        request.setAlarmState(AlarmInstanceAlarmState.CLEARED);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(AlarmInstanceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getDefinitionId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSourceRef()).isEqualTo("sourceRef-3");
        assertThat(entity.getRaisedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getClearedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getAckedBy()).isEqualTo("ackedBy-3");
        assertThat(entity.getAlarmState()).isEqualTo(AlarmInstanceAlarmState.CLEARED);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(AlarmInstanceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        AlarmInstance before = AlarmInstanceFixtures.entity();
        mapper.applyUpdate(new AlarmInstanceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
