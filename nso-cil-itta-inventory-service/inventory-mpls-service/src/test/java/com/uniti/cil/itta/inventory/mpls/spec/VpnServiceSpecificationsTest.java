package com.uniti.cil.itta.inventory.mpls.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.VpnServiceSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VpnServiceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(VpnServiceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        VpnServiceSearchCriteria criteria = new VpnServiceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(VpnServiceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
