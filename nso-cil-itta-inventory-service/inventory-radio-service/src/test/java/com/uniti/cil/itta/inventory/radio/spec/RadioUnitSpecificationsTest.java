package com.uniti.cil.itta.inventory.radio.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.RadioUnitSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RadioUnitSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RadioUnitSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RadioUnitSearchCriteria criteria = new RadioUnitSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RadioUnitSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
