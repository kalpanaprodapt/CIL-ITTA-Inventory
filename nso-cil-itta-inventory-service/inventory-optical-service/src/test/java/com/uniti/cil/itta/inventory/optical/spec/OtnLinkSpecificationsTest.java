package com.uniti.cil.itta.inventory.optical.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.OtnLinkSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OtnLinkSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(OtnLinkSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        OtnLinkSearchCriteria criteria = new OtnLinkSearchCriteria();
        criteria.setQ("  core ");
        assertThat(OtnLinkSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
