package com.uniti.cil.itta.inventory.port.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.LagGroupSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LagGroupSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(LagGroupSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        LagGroupSearchCriteria criteria = new LagGroupSearchCriteria();
        criteria.setQ("  core ");
        assertThat(LagGroupSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
