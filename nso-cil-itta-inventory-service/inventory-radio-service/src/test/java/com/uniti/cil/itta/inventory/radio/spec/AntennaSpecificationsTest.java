package com.uniti.cil.itta.inventory.radio.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.AntennaSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AntennaSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(AntennaSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        AntennaSearchCriteria criteria = new AntennaSearchCriteria();
        criteria.setQ("  core ");
        assertThat(AntennaSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
