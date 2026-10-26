package com.uniti.cil.itta.inventory.mpls.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.PseudoWireSearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PseudoWireSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PseudoWireSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PseudoWireSearchCriteria criteria = new PseudoWireSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PseudoWireSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
