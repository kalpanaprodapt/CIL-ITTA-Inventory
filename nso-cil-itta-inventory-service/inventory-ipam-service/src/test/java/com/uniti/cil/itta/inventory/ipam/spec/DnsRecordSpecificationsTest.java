package com.uniti.cil.itta.inventory.ipam.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.DnsRecordSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DnsRecordSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(DnsRecordSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        DnsRecordSearchCriteria criteria = new DnsRecordSearchCriteria();
        criteria.setQ("  core ");
        assertThat(DnsRecordSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
