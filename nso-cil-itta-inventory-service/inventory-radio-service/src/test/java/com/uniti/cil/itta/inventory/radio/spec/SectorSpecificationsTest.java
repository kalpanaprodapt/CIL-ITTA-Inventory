package com.uniti.cil.itta.inventory.radio.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.SectorSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SectorSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SectorSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SectorSearchCriteria criteria = new SectorSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SectorSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
