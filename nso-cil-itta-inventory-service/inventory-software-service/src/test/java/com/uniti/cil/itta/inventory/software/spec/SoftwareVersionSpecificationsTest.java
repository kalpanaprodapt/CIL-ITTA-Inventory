package com.uniti.cil.itta.inventory.software.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.SoftwareVersionSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoftwareVersionSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SoftwareVersionSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SoftwareVersionSearchCriteria criteria = new SoftwareVersionSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SoftwareVersionSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
