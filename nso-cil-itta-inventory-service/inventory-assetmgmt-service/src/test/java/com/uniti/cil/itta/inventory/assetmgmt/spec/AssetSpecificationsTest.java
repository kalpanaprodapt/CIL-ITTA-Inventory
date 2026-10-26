package com.uniti.cil.itta.inventory.assetmgmt.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssetSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AssetSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AssetSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
