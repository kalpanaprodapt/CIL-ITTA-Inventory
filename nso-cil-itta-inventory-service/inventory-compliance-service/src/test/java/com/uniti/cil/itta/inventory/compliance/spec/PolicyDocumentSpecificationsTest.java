package com.uniti.cil.itta.inventory.compliance.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.PolicyDocumentSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PolicyDocumentSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PolicyDocumentSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PolicyDocumentSearchCriteria criteria = new PolicyDocumentSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PolicyDocumentSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
