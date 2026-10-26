package com.uniti.cil.itta.inventory.service.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceInstanceSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceInstanceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ServiceInstanceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ServiceInstanceSearchCriteria criteria = new ServiceInstanceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ServiceInstanceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
