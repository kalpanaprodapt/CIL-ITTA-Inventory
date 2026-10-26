package com.uniti.cil.itta.inventory.discovery.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.ReconciliationRuleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationRuleMapperTest {

    private final ReconciliationRuleMapper mapper = new ReconciliationRuleMapper();

    @Test
    void toEntityCopiesEveryField() {
        ReconciliationRuleCreateRequest request = ReconciliationRuleFixtures.createRequest();
        ReconciliationRule entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRuleName()).isEqualTo(request.getRuleName());
        assertThat(entity.getMatchKey()).isEqualTo(request.getMatchKey());
        assertThat(entity.getAction()).isEqualTo(request.getAction());
        assertThat(entity.getPriority()).isEqualTo(request.getPriority());
        assertThat(entity.getExpression()).isEqualTo(request.getExpression());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        ReconciliationRuleResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRuleName()).isEqualTo(entity.getRuleName());
        assertThat(response.getMatchKey()).isEqualTo(entity.getMatchKey());
        assertThat(response.getAction()).isEqualTo(entity.getAction());
        assertThat(response.getPriority()).isEqualTo(entity.getPriority());
        assertThat(response.getExpression()).isEqualTo(entity.getExpression());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        ReconciliationRuleSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ReconciliationRuleFixtures.entity())).hasSameSizeAs(ReconciliationRuleMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        ReconciliationRuleUpdateRequest request = new ReconciliationRuleUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRuleName("ruleName-3");
        request.setMatchKey("matchKey-3");
        request.setAction(ReconciliationRuleAction.IGNORE);
        request.setPriority(13);
        request.setExpression("expression-3");
        request.setLabels("labels-3");
        request.setLifecycleStage(ReconciliationRuleLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRuleName()).isEqualTo("ruleName-3");
        assertThat(entity.getMatchKey()).isEqualTo("matchKey-3");
        assertThat(entity.getAction()).isEqualTo(ReconciliationRuleAction.IGNORE);
        assertThat(entity.getPriority()).isEqualTo(13);
        assertThat(entity.getExpression()).isEqualTo("expression-3");
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ReconciliationRuleLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        ReconciliationRule before = ReconciliationRuleFixtures.entity();
        mapper.applyUpdate(new ReconciliationRuleUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
