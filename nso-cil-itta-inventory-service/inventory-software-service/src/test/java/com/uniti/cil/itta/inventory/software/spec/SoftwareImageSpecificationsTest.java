package com.uniti.cil.itta.inventory.software.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.SoftwareImageSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoftwareImageSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SoftwareImageSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SoftwareImageSearchCriteria criteria = new SoftwareImageSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SoftwareImageSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
