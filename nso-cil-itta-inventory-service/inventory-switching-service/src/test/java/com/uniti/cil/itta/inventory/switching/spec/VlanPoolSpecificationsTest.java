package com.uniti.cil.itta.inventory.switching.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.VlanPoolSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VlanPoolSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VlanPoolSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VlanPoolSearchCriteria criteria = new VlanPoolSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VlanPoolSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
