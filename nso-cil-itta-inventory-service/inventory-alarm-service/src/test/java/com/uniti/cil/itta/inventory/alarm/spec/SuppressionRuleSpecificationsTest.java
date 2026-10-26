package com.uniti.cil.itta.inventory.alarm.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.SuppressionRuleSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SuppressionRuleSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SuppressionRuleSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SuppressionRuleSearchCriteria criteria = new SuppressionRuleSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SuppressionRuleSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
