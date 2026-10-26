package com.uniti.cil.itta.inventory.compliance.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceExceptionSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceExceptionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ComplianceExceptionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ComplianceExceptionSearchCriteria criteria = new ComplianceExceptionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ComplianceExceptionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
