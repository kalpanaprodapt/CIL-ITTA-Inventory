package com.uniti.cil.itta.inventory.assetmgmt.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetTagSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssetTagSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AssetTagSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AssetTagSearchCriteria criteria = new AssetTagSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AssetTagSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
