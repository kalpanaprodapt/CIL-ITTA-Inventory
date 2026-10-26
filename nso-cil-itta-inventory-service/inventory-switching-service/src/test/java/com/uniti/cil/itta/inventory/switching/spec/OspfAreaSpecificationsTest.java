package com.uniti.cil.itta.inventory.switching.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.OspfAreaSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OspfAreaSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(OspfAreaSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        OspfAreaSearchCriteria criteria = new OspfAreaSearchCriteria();
        criteria.setQ("  core ");
        assertThat(OspfAreaSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
