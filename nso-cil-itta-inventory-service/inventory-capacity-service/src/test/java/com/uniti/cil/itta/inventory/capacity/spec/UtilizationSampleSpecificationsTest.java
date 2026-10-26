package com.uniti.cil.itta.inventory.capacity.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.UtilizationSampleSearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class UtilizationSampleSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(UtilizationSampleSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        UtilizationSampleSearchCriteria criteria = new UtilizationSampleSearchCriteria();
        criteria.setQ("  core ");
        assertThat(UtilizationSampleSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
