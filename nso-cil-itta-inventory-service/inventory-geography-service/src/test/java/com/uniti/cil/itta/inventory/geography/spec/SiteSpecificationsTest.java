package com.uniti.cil.itta.inventory.geography.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.SiteSearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SiteSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SiteSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SiteSearchCriteria criteria = new SiteSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SiteSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
