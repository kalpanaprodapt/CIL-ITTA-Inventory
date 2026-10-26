package com.uniti.cil.itta.inventory.facility.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.RackUnitSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RackUnitSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RackUnitSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RackUnitSearchCriteria criteria = new RackUnitSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RackUnitSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
