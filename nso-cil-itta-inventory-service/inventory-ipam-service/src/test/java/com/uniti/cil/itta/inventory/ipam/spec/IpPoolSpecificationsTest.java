package com.uniti.cil.itta.inventory.ipam.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpPoolSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpPoolSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(IpPoolSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        IpPoolSearchCriteria criteria = new IpPoolSearchCriteria();
        criteria.setQ("  core ");
        assertThat(IpPoolSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
