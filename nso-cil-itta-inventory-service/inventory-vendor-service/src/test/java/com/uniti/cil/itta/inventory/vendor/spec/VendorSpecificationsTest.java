package com.uniti.cil.itta.inventory.vendor.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.VendorSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VendorSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VendorSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VendorSearchCriteria criteria = new VendorSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VendorSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
