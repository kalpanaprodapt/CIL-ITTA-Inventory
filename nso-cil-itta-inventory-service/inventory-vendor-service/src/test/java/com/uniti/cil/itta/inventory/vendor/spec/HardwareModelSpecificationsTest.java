package com.uniti.cil.itta.inventory.vendor.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.HardwareModelSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HardwareModelSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(HardwareModelSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        HardwareModelSearchCriteria criteria = new HardwareModelSearchCriteria();
        criteria.setQ("  core ");
        assertThat(HardwareModelSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
