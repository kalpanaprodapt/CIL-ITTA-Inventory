package com.uniti.cil.itta.inventory.discovery.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationResultSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationResultSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ReconciliationResultSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ReconciliationResultSearchCriteria criteria = new ReconciliationResultSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ReconciliationResultSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
