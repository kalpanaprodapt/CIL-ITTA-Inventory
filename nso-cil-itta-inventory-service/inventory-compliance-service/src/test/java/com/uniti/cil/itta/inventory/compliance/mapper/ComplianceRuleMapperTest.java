package com.uniti.cil.itta.inventory.compliance.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceRuleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceRuleMapperTest {

    private final ComplianceRuleMapper mapper = new ComplianceRuleMapper();

    @Test
    void toEntityCopiesEveryField() {
        ComplianceRuleCreateRequest request = ComplianceRuleFixtures.createRequest();
        ComplianceRule entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRuleCode()).isEqualTo(request.getRuleCode());
        assertThat(entity.getFramework()).isEqualTo(request.getFramework());
        assertThat(entity.getSeverity()).isEqualTo(request.getSeverity());
        assertThat(entity.getExpression()).isEqualTo(request.getExpression());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        ComplianceRuleResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRuleCode()).isEqualTo(entity.getRuleCode());
        assertThat(response.getFramework()).isEqualTo(entity.getFramework());
        assertThat(response.getSeverity()).isEqualTo(entity.getSeverity());
        assertThat(response.getExpression()).isEqualTo(entity.getExpression());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        ComplianceRuleSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ComplianceRuleFixtures.entity())).hasSameSizeAs(ComplianceRuleMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        ComplianceRuleUpdateRequest request = new ComplianceRuleUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRuleCode("ruleCode-3");
        request.setFramework(ComplianceRuleFramework.PCI);
        request.setSeverity(ComplianceRuleSeverity.HIGH);
        request.setExpression("expression-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ComplianceRuleCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRuleCode()).isEqualTo("ruleCode-3");
        assertThat(entity.getFramework()).isEqualTo(ComplianceRuleFramework.PCI);
        assertThat(entity.getSeverity()).isEqualTo(ComplianceRuleSeverity.HIGH);
        assertThat(entity.getExpression()).isEqualTo("expression-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ComplianceRuleCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ComplianceRule entity = ComplianceRuleFixtures.entity();
        ComplianceRule before = ComplianceRuleFixtures.entity();
        mapper.applyUpdate(new ComplianceRuleUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
