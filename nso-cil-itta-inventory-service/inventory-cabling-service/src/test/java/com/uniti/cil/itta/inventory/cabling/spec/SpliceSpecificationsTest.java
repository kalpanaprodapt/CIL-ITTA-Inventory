package com.uniti.cil.itta.inventory.cabling.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.SpliceSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SpliceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SpliceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SpliceSearchCriteria criteria = new SpliceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SpliceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
