package com.uniti.cil.itta.inventory.service.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceDependencySearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceDependencySpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ServiceDependencySpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ServiceDependencySearchCriteria criteria = new ServiceDependencySearchCriteria();
        criteria.setQ("  core ");
        assertThat(ServiceDependencySpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
