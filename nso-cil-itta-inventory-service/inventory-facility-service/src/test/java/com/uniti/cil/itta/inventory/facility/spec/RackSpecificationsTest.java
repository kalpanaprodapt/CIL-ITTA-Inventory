package com.uniti.cil.itta.inventory.facility.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.RackSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RackSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RackSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RackSearchCriteria criteria = new RackSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RackSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
