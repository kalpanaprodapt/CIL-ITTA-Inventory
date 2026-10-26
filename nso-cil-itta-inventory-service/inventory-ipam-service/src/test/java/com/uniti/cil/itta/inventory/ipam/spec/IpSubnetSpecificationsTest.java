package com.uniti.cil.itta.inventory.ipam.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpSubnetSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpSubnetSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(IpSubnetSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        IpSubnetSearchCriteria criteria = new IpSubnetSearchCriteria();
        criteria.setQ("  core ");
        assertThat(IpSubnetSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
