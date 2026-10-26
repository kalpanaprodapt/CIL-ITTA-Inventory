package com.uniti.cil.itta.inventory.optical.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.OpticalChannelSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpticalChannelSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(OpticalChannelSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        OpticalChannelSearchCriteria criteria = new OpticalChannelSearchCriteria();
        criteria.setQ("  core ");
        assertThat(OpticalChannelSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
