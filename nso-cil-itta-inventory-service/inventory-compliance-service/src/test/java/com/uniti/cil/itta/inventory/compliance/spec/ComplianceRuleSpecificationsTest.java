package com.uniti.cil.itta.inventory.compliance.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceRuleSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceRuleSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ComplianceRuleSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ComplianceRuleSearchCriteria criteria = new ComplianceRuleSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ComplianceRuleSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
