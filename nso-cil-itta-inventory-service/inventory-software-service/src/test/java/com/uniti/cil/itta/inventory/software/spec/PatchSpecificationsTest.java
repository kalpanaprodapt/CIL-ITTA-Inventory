package com.uniti.cil.itta.inventory.software.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.PatchSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PatchSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PatchSearchCriteria criteria = new PatchSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PatchSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
