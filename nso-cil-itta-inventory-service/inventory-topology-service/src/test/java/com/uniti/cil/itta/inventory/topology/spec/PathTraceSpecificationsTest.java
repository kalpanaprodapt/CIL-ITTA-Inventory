package com.uniti.cil.itta.inventory.topology.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.PathTraceSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PathTraceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PathTraceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PathTraceSearchCriteria criteria = new PathTraceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PathTraceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
