package com.uniti.cil.itta.inventory.radio.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.BaseStationSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BaseStationSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(BaseStationSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        BaseStationSearchCriteria criteria = new BaseStationSearchCriteria();
        criteria.setQ("  core ");
        assertThat(BaseStationSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
