package com.uniti.cil.itta.inventory.facility.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.FloorSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FloorSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(FloorSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        FloorSearchCriteria criteria = new FloorSearchCriteria();
        criteria.setQ("  core ");
        assertThat(FloorSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
