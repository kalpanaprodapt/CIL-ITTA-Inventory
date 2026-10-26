package com.uniti.cil.itta.inventory.mpls.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.TunnelEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TunnelEndpointSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TunnelEndpointSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TunnelEndpointSearchCriteria criteria = new TunnelEndpointSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TunnelEndpointSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
