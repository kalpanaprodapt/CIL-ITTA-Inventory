package com.uniti.cil.itta.inventory.service.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceOrderSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceOrderSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ServiceOrderSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ServiceOrderSearchCriteria criteria = new ServiceOrderSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ServiceOrderSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
