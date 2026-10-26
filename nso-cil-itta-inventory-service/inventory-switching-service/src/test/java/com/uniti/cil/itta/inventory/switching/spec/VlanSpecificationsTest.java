package com.uniti.cil.itta.inventory.switching.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.VlanSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VlanSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VlanSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VlanSearchCriteria criteria = new VlanSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VlanSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
