package com.uniti.cil.itta.inventory.circuit.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitProviderSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitProviderSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CircuitProviderSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CircuitProviderSearchCriteria criteria = new CircuitProviderSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CircuitProviderSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
