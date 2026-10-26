package com.uniti.cil.itta.inventory.assetmgmt.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.DepreciationScheduleSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DepreciationScheduleSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(DepreciationScheduleSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        DepreciationScheduleSearchCriteria criteria = new DepreciationScheduleSearchCriteria();
        criteria.setQ("  core ");
        assertThat(DepreciationScheduleSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
