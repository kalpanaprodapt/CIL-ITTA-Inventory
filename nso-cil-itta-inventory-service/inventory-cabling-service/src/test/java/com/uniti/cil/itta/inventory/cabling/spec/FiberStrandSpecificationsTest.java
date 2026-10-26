package com.uniti.cil.itta.inventory.cabling.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.FiberStrandSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FiberStrandSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(FiberStrandSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        FiberStrandSearchCriteria criteria = new FiberStrandSearchCriteria();
        criteria.setQ("  core ");
        assertThat(FiberStrandSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
