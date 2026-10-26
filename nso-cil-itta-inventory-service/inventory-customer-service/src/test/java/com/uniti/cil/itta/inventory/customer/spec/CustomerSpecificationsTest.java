package com.uniti.cil.itta.inventory.customer.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.CustomerSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CustomerSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CustomerSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
