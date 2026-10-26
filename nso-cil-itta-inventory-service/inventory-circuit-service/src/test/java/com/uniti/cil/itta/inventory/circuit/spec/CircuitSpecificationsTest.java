package com.uniti.cil.itta.inventory.circuit.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CircuitSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CircuitSearchCriteria criteria = new CircuitSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CircuitSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
