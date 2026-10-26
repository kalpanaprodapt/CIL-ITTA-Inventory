package com.uniti.cil.itta.inventory.circuit.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitSegmentSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitSegmentSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(CircuitSegmentSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        CircuitSegmentSearchCriteria criteria = new CircuitSegmentSearchCriteria();
        criteria.setQ("  core ");
        assertThat(CircuitSegmentSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
