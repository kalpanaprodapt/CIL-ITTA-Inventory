package com.uniti.cil.itta.inventory.device.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.PowerSupplySearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PowerSupplySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PowerSupplySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PowerSupplySearchCriteria criteria = new PowerSupplySearchCriteria();
        criteria.setQ("  core ");
        assertThat(PowerSupplySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
