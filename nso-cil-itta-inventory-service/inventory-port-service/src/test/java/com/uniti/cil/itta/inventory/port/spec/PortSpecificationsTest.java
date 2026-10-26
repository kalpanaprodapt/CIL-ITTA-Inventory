package com.uniti.cil.itta.inventory.port.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.PortSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PortSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(PortSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        PortSearchCriteria criteria = new PortSearchCriteria();
        criteria.setQ("  core ");
        assertThat(PortSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
