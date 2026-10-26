package com.uniti.cil.itta.inventory.integration.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.SyncJobSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncJobSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SyncJobSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SyncJobSearchCriteria criteria = new SyncJobSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SyncJobSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
