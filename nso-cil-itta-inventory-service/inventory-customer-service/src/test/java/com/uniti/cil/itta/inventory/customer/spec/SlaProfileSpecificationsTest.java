package com.uniti.cil.itta.inventory.customer.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.SlaProfileSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SlaProfileSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SlaProfileSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SlaProfileSearchCriteria criteria = new SlaProfileSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SlaProfileSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
