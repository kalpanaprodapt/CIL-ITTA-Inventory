package com.uniti.cil.itta.inventory.integration.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.SyncMappingSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncMappingSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SyncMappingSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SyncMappingSearchCriteria criteria = new SyncMappingSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SyncMappingSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
