package com.uniti.cil.itta.inventory.capacity.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.ThresholdPolicyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ThresholdPolicyMapperTest {

    private final ThresholdPolicyMapper mapper = new ThresholdPolicyMapper();

    @Test
    void toEntityCopiesEveryField() {
        ThresholdPolicyCreateRequest request = ThresholdPolicyFixtures.createRequest();
        ThresholdPolicy entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPolicyName()).isEqualTo(request.getPolicyName());
        assertThat(entity.getMetric()).isEqualTo(request.getMetric());
        assertThat(entity.getWarnPct()).isEqualTo(request.getWarnPct());
        assertThat(entity.getCritPct()).isEqualTo(request.getCritPct());
        assertThat(entity.getEvaluationWindowMinutes()).isEqualTo(request.getEvaluationWindowMinutes());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        ThresholdPolicyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPolicyName()).isEqualTo(entity.getPolicyName());
        assertThat(response.getMetric()).isEqualTo(entity.getMetric());
        assertThat(response.getWarnPct()).isEqualTo(entity.getWarnPct());
        assertThat(response.getCritPct()).isEqualTo(entity.getCritPct());
        assertThat(response.getEvaluationWindowMinutes()).isEqualTo(entity.getEvaluationWindowMinutes());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        ThresholdPolicySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ThresholdPolicyFixtures.entity())).hasSameSizeAs(ThresholdPolicyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        ThresholdPolicyUpdateRequest request = new ThresholdPolicyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPolicyName("policyName-3");
        request.setMetric("metric-3");
        request.setWarnPct(new BigDecimal("15.50"));
        request.setCritPct(new BigDecimal("15.50"));
        request.setEvaluationWindowMinutes(13);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ThresholdPolicyCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPolicyName()).isEqualTo("policyName-3");
        assertThat(entity.getMetric()).isEqualTo("metric-3");
        assertThat(entity.getWarnPct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCritPct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getEvaluationWindowMinutes()).isEqualTo(13);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ThresholdPolicyCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        ThresholdPolicy before = ThresholdPolicyFixtures.entity();
        mapper.applyUpdate(new ThresholdPolicyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
