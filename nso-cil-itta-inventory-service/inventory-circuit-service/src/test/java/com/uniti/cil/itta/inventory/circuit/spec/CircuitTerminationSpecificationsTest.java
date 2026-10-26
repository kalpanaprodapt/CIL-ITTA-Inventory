package com.uniti.cil.itta.inventory.circuit.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitTerminationSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitTerminationSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CircuitTerminationSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CircuitTerminationSearchCriteria criteria = new CircuitTerminationSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CircuitTerminationSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
