package com.uniti.cil.itta.inventory.vendor.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.WarrantySearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WarrantySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(WarrantySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        WarrantySearchCriteria criteria = new WarrantySearchCriteria();
        criteria.setQ("  core ");
        assertThat(WarrantySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
