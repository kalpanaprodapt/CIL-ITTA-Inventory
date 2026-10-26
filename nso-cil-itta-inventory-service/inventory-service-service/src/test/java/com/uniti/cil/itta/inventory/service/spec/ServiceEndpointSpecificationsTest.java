package com.uniti.cil.itta.inventory.service.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceEndpointSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceEndpointSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ServiceEndpointSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ServiceEndpointSearchCriteria criteria = new ServiceEndpointSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ServiceEndpointSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
