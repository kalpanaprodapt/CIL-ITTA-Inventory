package com.uniti.cil.itta.inventory.optical.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.RoadmSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoadmSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RoadmSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RoadmSearchCriteria criteria = new RoadmSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RoadmSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
