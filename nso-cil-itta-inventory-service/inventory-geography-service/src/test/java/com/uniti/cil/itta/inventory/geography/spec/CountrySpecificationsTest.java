package com.uniti.cil.itta.inventory.geography.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.CountrySearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CountrySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CountrySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CountrySearchCriteria criteria = new CountrySearchCriteria();
        criteria.setQ("  core ");
        assertThat(CountrySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
