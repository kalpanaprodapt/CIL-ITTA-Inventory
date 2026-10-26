package com.uniti.cil.itta.inventory.customer.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.CustomerAccountSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerAccountSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CustomerAccountSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CustomerAccountSearchCriteria criteria = new CustomerAccountSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CustomerAccountSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
