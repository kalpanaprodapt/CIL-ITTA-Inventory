package com.uniti.cil.itta.inventory.capacity.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.ThresholdPolicySearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ThresholdPolicySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ThresholdPolicySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ThresholdPolicySearchCriteria criteria = new ThresholdPolicySearchCriteria();
        criteria.setQ("  core ");
        assertThat(ThresholdPolicySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
