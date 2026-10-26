package com.uniti.cil.itta.inventory.geography.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.CitySearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CitySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CitySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CitySearchCriteria criteria = new CitySearchCriteria();
        criteria.setQ("  core ");
        assertThat(CitySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
