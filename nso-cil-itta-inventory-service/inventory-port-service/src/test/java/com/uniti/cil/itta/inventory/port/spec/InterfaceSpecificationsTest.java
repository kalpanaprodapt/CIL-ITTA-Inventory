package com.uniti.cil.itta.inventory.port.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.InterfaceSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class InterfaceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(InterfaceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        InterfaceSearchCriteria criteria = new InterfaceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(InterfaceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
