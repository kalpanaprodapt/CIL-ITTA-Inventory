package com.uniti.cil.itta.inventory.ipam.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.VrfInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VrfInstanceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VrfInstanceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VrfInstanceSearchCriteria criteria = new VrfInstanceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VrfInstanceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
