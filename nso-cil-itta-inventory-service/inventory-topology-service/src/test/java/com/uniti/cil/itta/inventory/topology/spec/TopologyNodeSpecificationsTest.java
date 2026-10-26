package com.uniti.cil.itta.inventory.topology.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyNodeSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyNodeSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TopologyNodeSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TopologyNodeSearchCriteria criteria = new TopologyNodeSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TopologyNodeSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
