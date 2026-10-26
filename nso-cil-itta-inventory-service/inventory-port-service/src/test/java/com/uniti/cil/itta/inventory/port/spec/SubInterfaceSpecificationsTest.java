package com.uniti.cil.itta.inventory.port.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.SubInterfaceSearchCriteria;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SubInterfaceSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(SubInterfaceSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        SubInterfaceSearchCriteria criteria = new SubInterfaceSearchCriteria();
        criteria.setQ("  core ");
        assertThat(SubInterfaceSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
