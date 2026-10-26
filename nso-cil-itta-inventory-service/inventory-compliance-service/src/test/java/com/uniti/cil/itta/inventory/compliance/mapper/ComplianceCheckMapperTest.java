package com.uniti.cil.itta.inventory.compliance.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceCheckFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceCheckMapperTest {

    private final ComplianceCheckMapper mapper = new ComplianceCheckMapper();

    @Test
    void toEntityCopiesEveryField() {
        ComplianceCheckCreateRequest request = ComplianceCheckFixtures.createRequest();
        ComplianceCheck entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRuleId()).isEqualTo(request.getRuleId());
        assertThat(entity.getTargetRef()).isEqualTo(request.getTargetRef());
        assertThat(entity.getCheckedAt()).isEqualTo(request.getCheckedAt());
        assertThat(entity.getResult()).isEqualTo(request.getResult());
        assertThat(entity.getEvidence()).isEqualTo(request.getEvidence());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        ComplianceCheckResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRuleId()).isEqualTo(entity.getRuleId());
        assertThat(response.getTargetRef()).isEqualTo(entity.getTargetRef());
        assertThat(response.getCheckedAt()).isEqualTo(entity.getCheckedAt());
        assertThat(response.getResult()).isEqualTo(entity.getResult());
        assertThat(response.getEvidence()).isEqualTo(entity.getEvidence());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        ComplianceCheckSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ComplianceCheckFixtures.entity())).hasSameSizeAs(ComplianceCheckMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        ComplianceCheckUpdateRequest request = new ComplianceCheckUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTargetRef("targetRef-3");
        request.setCheckedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setResult(ComplianceCheckResult.WARN);
        request.setEvidence("evidence-3");
        request.setCriticality(ComplianceCheckCriticality.HIGH);
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
        assertThat(entity.getRuleId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTargetRef()).isEqualTo("targetRef-3");
        assertThat(entity.getCheckedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getResult()).isEqualTo(ComplianceCheckResult.WARN);
        assertThat(entity.getEvidence()).isEqualTo("evidence-3");
        assertThat(entity.getCriticality()).isEqualTo(ComplianceCheckCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ComplianceCheck entity = ComplianceCheckFixtures.entity();
        ComplianceCheck before = ComplianceCheckFixtures.entity();
        mapper.applyUpdate(new ComplianceCheckUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
