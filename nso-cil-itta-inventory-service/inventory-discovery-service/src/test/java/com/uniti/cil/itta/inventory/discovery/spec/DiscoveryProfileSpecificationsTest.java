package com.uniti.cil.itta.inventory.discovery.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryProfileSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveryProfileSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(DiscoveryProfileSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        DiscoveryProfileSearchCriteria criteria = new DiscoveryProfileSearchCriteria();
        criteria.setQ("  core ");
        assertThat(DiscoveryProfileSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
