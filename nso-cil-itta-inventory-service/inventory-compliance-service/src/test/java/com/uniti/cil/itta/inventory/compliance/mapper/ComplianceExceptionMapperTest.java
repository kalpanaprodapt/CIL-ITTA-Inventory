package com.uniti.cil.itta.inventory.compliance.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceExceptionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceExceptionMapperTest {

    private final ComplianceExceptionMapper mapper = new ComplianceExceptionMapper();

    @Test
    void toEntityCopiesEveryField() {
        ComplianceExceptionCreateRequest request = ComplianceExceptionFixtures.createRequest();
        ComplianceException entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRuleId()).isEqualTo(request.getRuleId());
        assertThat(entity.getTargetRef()).isEqualTo(request.getTargetRef());
        assertThat(entity.getJustification()).isEqualTo(request.getJustification());
        assertThat(entity.getApprovedBy()).isEqualTo(request.getApprovedBy());
        assertThat(entity.getExpiresOn()).isEqualTo(request.getExpiresOn());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        ComplianceExceptionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRuleId()).isEqualTo(entity.getRuleId());
        assertThat(response.getTargetRef()).isEqualTo(entity.getTargetRef());
        assertThat(response.getJustification()).isEqualTo(entity.getJustification());
        assertThat(response.getApprovedBy()).isEqualTo(entity.getApprovedBy());
        assertThat(response.getExpiresOn()).isEqualTo(entity.getExpiresOn());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        ComplianceExceptionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ComplianceExceptionFixtures.entity())).hasSameSizeAs(ComplianceExceptionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        ComplianceExceptionUpdateRequest request = new ComplianceExceptionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTargetRef("targetRef-3");
        request.setJustification("justification-3");
        request.setApprovedBy("approvedBy-3");
        request.setExpiresOn(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(ComplianceExceptionLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRuleId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTargetRef()).isEqualTo("targetRef-3");
        assertThat(entity.getJustification()).isEqualTo("justification-3");
        assertThat(entity.getApprovedBy()).isEqualTo("approvedBy-3");
        assertThat(entity.getExpiresOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ComplianceExceptionLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        ComplianceException before = ComplianceExceptionFixtures.entity();
        mapper.applyUpdate(new ComplianceExceptionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
