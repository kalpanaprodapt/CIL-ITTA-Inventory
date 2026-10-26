package com.uniti.cil.itta.inventory.switching.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.RouteTargetSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RouteTargetSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RouteTargetSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RouteTargetSearchCriteria criteria = new RouteTargetSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RouteTargetSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
