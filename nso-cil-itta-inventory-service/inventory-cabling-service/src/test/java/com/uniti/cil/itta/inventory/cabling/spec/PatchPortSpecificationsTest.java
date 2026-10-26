package com.uniti.cil.itta.inventory.cabling.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.PatchPortSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchPortSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PatchPortSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PatchPortSearchCriteria criteria = new PatchPortSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PatchPortSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
