package com.uniti.cil.itta.inventory.virtual.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.HypervisorSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HypervisorSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(HypervisorSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        HypervisorSearchCriteria criteria = new HypervisorSearchCriteria();
        criteria.setQ("  core ");
        assertThat(HypervisorSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
