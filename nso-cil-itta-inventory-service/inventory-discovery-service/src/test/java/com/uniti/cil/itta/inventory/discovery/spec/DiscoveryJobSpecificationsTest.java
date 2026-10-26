package com.uniti.cil.itta.inventory.discovery.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryJobSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveryJobSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(DiscoveryJobSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        DiscoveryJobSearchCriteria criteria = new DiscoveryJobSearchCriteria();
        criteria.setQ("  core ");
        assertThat(DiscoveryJobSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
