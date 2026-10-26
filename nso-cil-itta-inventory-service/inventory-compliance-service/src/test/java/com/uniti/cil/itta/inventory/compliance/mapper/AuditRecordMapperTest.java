package com.uniti.cil.itta.inventory.compliance.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.AuditRecordFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AuditRecordMapperTest {

    private final AuditRecordMapper mapper = new AuditRecordMapper();

    @Test
    void toEntityCopiesEveryField() {
        AuditRecordCreateRequest request = AuditRecordFixtures.createRequest();
        AuditRecord entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getActor()).isEqualTo(request.getActor());
        assertThat(entity.getAction()).isEqualTo(request.getAction());
        assertThat(entity.getEntityRef()).isEqualTo(request.getEntityRef());
        assertThat(entity.getOccurredAt()).isEqualTo(request.getOccurredAt());
        assertThat(entity.getBeforeState()).isEqualTo(request.getBeforeState());
        assertThat(entity.getAfterState()).isEqualTo(request.getAfterState());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        AuditRecord entity = AuditRecordFixtures.entity();
        AuditRecordResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getActor()).isEqualTo(entity.getActor());
        assertThat(response.getAction()).isEqualTo(entity.getAction());
        assertThat(response.getEntityRef()).isEqualTo(entity.getEntityRef());
        assertThat(response.getOccurredAt()).isEqualTo(entity.getOccurredAt());
        assertThat(response.getBeforeState()).isEqualTo(entity.getBeforeState());
        assertThat(response.getAfterState()).isEqualTo(entity.getAfterState());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        AuditRecord entity = AuditRecordFixtures.entity();
        AuditRecordSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AuditRecordFixtures.entity())).hasSameSizeAs(AuditRecordMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        AuditRecord entity = AuditRecordFixtures.entity();
        AuditRecordUpdateRequest request = new AuditRecordUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setActor("actor-3");
        request.setAction("action-3");
        request.setEntityRef("entityRef-3");
        request.setOccurredAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setBeforeState("beforeState-3");
        request.setAfterState("afterState-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(AuditRecordLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getActor()).isEqualTo("actor-3");
        assertThat(entity.getAction()).isEqualTo("action-3");
        assertThat(entity.getEntityRef()).isEqualTo("entityRef-3");
        assertThat(entity.getOccurredAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getBeforeState()).isEqualTo("beforeState-3");
        assertThat(entity.getAfterState()).isEqualTo("afterState-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(AuditRecordLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        AuditRecord entity = AuditRecordFixtures.entity();
        AuditRecord before = AuditRecordFixtures.entity();
        mapper.applyUpdate(new AuditRecordUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
