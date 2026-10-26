package com.uniti.cil.itta.inventory.customer.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.ContractSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ContractSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ContractSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ContractSearchCriteria criteria = new ContractSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ContractSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
