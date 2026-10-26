package com.uniti.cil.itta.inventory.software.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.LicensePoolSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LicensePoolSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(LicensePoolSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        LicensePoolSearchCriteria criteria = new LicensePoolSearchCriteria();
        criteria.setQ("  core ");
        assertThat(LicensePoolSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
