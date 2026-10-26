package com.uniti.cil.itta.inventory.facility.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.PowerFeedSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PowerFeedSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PowerFeedSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PowerFeedSearchCriteria criteria = new PowerFeedSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PowerFeedSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
