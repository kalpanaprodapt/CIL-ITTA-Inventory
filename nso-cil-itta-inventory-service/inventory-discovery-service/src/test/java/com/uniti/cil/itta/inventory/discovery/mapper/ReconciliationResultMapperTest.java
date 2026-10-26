package com.uniti.cil.itta.inventory.discovery.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.ReconciliationResultFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationResultMapperTest {

    private final ReconciliationResultMapper mapper = new ReconciliationResultMapper();

    @Test
    void toEntityCopiesEveryField() {
        ReconciliationResultCreateRequest request = ReconciliationResultFixtures.createRequest();
        ReconciliationResult entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getJobId()).isEqualTo(request.getJobId());
        assertThat(entity.getRuleId()).isEqualTo(request.getRuleId());
        assertThat(entity.getOutcome()).isEqualTo(request.getOutcome());
        assertThat(entity.getDiffSummary()).isEqualTo(request.getDiffSummary());
        assertThat(entity.getResolvedBy()).isEqualTo(request.getResolvedBy());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        ReconciliationResultResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getJobId()).isEqualTo(entity.getJobId());
        assertThat(response.getRuleId()).isEqualTo(entity.getRuleId());
        assertThat(response.getOutcome()).isEqualTo(entity.getOutcome());
        assertThat(response.getDiffSummary()).isEqualTo(entity.getDiffSummary());
        assertThat(response.getResolvedBy()).isEqualTo(entity.getResolvedBy());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        ReconciliationResultSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ReconciliationResultFixtures.entity())).hasSameSizeAs(ReconciliationResultMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        ReconciliationResultUpdateRequest request = new ReconciliationResultUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setOutcome(ReconciliationResultOutcome.CONFLICT);
        request.setDiffSummary("diffSummary-3");
        request.setResolvedBy("resolvedBy-3");
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ReconciliationResultCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getJobId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRuleId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getOutcome()).isEqualTo(ReconciliationResultOutcome.CONFLICT);
        assertThat(entity.getDiffSummary()).isEqualTo("diffSummary-3");
        assertThat(entity.getResolvedBy()).isEqualTo("resolvedBy-3");
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ReconciliationResultCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ReconciliationResult entity = ReconciliationResultFixtures.entity();
        ReconciliationResult before = ReconciliationResultFixtures.entity();
        mapper.applyUpdate(new ReconciliationResultUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
