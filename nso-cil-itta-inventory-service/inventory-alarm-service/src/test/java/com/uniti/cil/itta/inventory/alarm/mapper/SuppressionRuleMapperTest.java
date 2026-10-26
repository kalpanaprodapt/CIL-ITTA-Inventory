package com.uniti.cil.itta.inventory.alarm.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.SuppressionRuleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SuppressionRuleMapperTest {

    private final SuppressionRuleMapper mapper = new SuppressionRuleMapper();

    @Test
    void toEntityCopiesEveryField() {
        SuppressionRuleCreateRequest request = SuppressionRuleFixtures.createRequest();
        SuppressionRule entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRuleName()).isEqualTo(request.getRuleName());
        assertThat(entity.getMatchExpression()).isEqualTo(request.getMatchExpression());
        assertThat(entity.getStartsAt()).isEqualTo(request.getStartsAt());
        assertThat(entity.getEndsAt()).isEqualTo(request.getEndsAt());
        assertThat(entity.getReason()).isEqualTo(request.getReason());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        SuppressionRuleResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRuleName()).isEqualTo(entity.getRuleName());
        assertThat(response.getMatchExpression()).isEqualTo(entity.getMatchExpression());
        assertThat(response.getStartsAt()).isEqualTo(entity.getStartsAt());
        assertThat(response.getEndsAt()).isEqualTo(entity.getEndsAt());
        assertThat(response.getReason()).isEqualTo(entity.getReason());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        SuppressionRuleSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SuppressionRuleFixtures.entity())).hasSameSizeAs(SuppressionRuleMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        SuppressionRuleUpdateRequest request = new SuppressionRuleUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRuleName("ruleName-3");
        request.setMatchExpression("matchExpression-3");
        request.setStartsAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setEndsAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setReason("reason-3");
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(SuppressionRuleCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRuleName()).isEqualTo("ruleName-3");
        assertThat(entity.getMatchExpression()).isEqualTo("matchExpression-3");
        assertThat(entity.getStartsAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getEndsAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getReason()).isEqualTo("reason-3");
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SuppressionRuleCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SuppressionRule entity = SuppressionRuleFixtures.entity();
        SuppressionRule before = SuppressionRuleFixtures.entity();
        mapper.applyUpdate(new SuppressionRuleUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
