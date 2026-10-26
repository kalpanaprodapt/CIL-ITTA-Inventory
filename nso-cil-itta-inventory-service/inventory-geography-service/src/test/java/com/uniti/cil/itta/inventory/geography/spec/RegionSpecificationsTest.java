package com.uniti.cil.itta.inventory.geography.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.RegionSearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RegionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RegionSearchCriteria criteria = new RegionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RegionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
