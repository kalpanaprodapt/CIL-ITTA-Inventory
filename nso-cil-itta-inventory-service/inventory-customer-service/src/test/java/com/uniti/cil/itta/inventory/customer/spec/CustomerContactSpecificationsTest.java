package com.uniti.cil.itta.inventory.customer.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.CustomerContactSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerContactSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CustomerContactSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CustomerContactSearchCriteria criteria = new CustomerContactSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CustomerContactSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
