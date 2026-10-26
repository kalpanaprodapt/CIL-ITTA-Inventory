package com.uniti.cil.itta.inventory.topology.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyEdgeSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyEdgeSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TopologyEdgeSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TopologyEdgeSearchCriteria criteria = new TopologyEdgeSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TopologyEdgeSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
