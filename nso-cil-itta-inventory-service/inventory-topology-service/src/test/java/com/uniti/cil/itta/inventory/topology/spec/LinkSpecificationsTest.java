package com.uniti.cil.itta.inventory.topology.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.LinkSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LinkSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(LinkSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        LinkSearchCriteria criteria = new LinkSearchCriteria();
        criteria.setQ("  core ");
        assertThat(LinkSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
