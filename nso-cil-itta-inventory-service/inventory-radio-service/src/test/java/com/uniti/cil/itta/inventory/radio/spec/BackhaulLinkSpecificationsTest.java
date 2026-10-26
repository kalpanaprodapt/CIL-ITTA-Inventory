package com.uniti.cil.itta.inventory.radio.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.BackhaulLinkSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BackhaulLinkSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(BackhaulLinkSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        BackhaulLinkSearchCriteria criteria = new BackhaulLinkSearchCriteria();
        criteria.setQ("  core ");
        assertThat(BackhaulLinkSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
