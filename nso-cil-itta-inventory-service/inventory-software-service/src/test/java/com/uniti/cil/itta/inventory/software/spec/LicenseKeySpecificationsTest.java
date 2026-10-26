package com.uniti.cil.itta.inventory.software.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.LicenseKeySearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LicenseKeySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(LicenseKeySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        LicenseKeySearchCriteria criteria = new LicenseKeySearchCriteria();
        criteria.setQ("  core ");
        assertThat(LicenseKeySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
