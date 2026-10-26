package com.uniti.cil.itta.inventory.ipam.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpAddressSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpAddressSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(IpAddressSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        IpAddressSearchCriteria criteria = new IpAddressSearchCriteria();
        criteria.setQ("  core ");
        assertThat(IpAddressSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
