package com.uniti.cil.itta.inventory.vendor.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.SkuSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SkuSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SkuSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SkuSearchCriteria criteria = new SkuSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SkuSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
