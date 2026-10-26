package com.uniti.cil.itta.inventory.capacity.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.ForecastModelSearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ForecastModelSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ForecastModelSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ForecastModelSearchCriteria criteria = new ForecastModelSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ForecastModelSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
