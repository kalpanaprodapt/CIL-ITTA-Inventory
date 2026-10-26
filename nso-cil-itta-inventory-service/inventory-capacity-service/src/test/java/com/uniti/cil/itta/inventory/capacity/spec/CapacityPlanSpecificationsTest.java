package com.uniti.cil.itta.inventory.capacity.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.CapacityPlanSearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CapacityPlanSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CapacityPlanSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CapacityPlanSearchCriteria criteria = new CapacityPlanSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CapacityPlanSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
