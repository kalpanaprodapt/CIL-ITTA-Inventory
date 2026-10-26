package com.uniti.cil.itta.inventory.topology.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyViewSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyViewSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TopologyViewSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TopologyViewSearchCriteria criteria = new TopologyViewSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TopologyViewSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
