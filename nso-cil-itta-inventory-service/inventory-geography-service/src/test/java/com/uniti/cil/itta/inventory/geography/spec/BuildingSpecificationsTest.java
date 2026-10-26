package com.uniti.cil.itta.inventory.geography.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.BuildingSearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BuildingSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(BuildingSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        BuildingSearchCriteria criteria = new BuildingSearchCriteria();
        criteria.setQ("  core ");
        assertThat(BuildingSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
