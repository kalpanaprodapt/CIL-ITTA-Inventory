package com.uniti.cil.itta.inventory.optical.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.AmplifierSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmplifierSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AmplifierSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AmplifierSearchCriteria criteria = new AmplifierSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AmplifierSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
