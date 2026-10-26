package com.uniti.cil.itta.inventory.compliance.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceCheckSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceCheckSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ComplianceCheckSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ComplianceCheckSearchCriteria criteria = new ComplianceCheckSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ComplianceCheckSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
