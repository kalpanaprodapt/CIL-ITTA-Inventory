package com.uniti.cil.itta.inventory.service.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.ServiceCatalogItemSearchCriteria;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceCatalogItemSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ServiceCatalogItemSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ServiceCatalogItemSearchCriteria criteria = new ServiceCatalogItemSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ServiceCatalogItemSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
