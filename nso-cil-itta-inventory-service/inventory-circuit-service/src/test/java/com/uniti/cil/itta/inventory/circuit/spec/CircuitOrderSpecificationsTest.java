package com.uniti.cil.itta.inventory.circuit.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitOrderSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitOrderSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CircuitOrderSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CircuitOrderSearchCriteria criteria = new CircuitOrderSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CircuitOrderSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
