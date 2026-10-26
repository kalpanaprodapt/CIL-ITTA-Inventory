package com.uniti.cil.itta.inventory.cabling.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.CableSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CableSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CableSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CableSearchCriteria criteria = new CableSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CableSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
