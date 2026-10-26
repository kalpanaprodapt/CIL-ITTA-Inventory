package com.uniti.cil.itta.inventory.vendor.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.SupportContractSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SupportContractSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SupportContractSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SupportContractSearchCriteria criteria = new SupportContractSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SupportContractSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
