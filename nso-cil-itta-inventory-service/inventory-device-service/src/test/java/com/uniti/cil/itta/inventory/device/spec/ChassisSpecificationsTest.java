package com.uniti.cil.itta.inventory.device.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.ChassisSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChassisSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ChassisSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ChassisSearchCriteria criteria = new ChassisSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ChassisSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
