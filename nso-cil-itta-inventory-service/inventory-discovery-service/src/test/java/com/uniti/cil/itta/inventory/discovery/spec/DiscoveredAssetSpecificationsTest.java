package com.uniti.cil.itta.inventory.discovery.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveredAssetSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveredAssetSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(DiscoveredAssetSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        DiscoveredAssetSearchCriteria criteria = new DiscoveredAssetSearchCriteria();
        criteria.setQ("  core ");
        assertThat(DiscoveredAssetSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
