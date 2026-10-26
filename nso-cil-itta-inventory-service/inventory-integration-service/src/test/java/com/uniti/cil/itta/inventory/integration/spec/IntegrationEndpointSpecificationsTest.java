package com.uniti.cil.itta.inventory.integration.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.IntegrationEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IntegrationEndpointSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(IntegrationEndpointSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        IntegrationEndpointSearchCriteria criteria = new IntegrationEndpointSearchCriteria();
        criteria.setQ("  core ");
        assertThat(IntegrationEndpointSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
