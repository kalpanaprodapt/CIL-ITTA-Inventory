package com.uniti.cil.itta.inventory.discovery.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationRuleSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationRuleSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ReconciliationRuleSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ReconciliationRuleSearchCriteria criteria = new ReconciliationRuleSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ReconciliationRuleSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
