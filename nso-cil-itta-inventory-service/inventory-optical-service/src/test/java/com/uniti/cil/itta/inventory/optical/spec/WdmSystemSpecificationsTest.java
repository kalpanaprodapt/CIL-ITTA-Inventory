package com.uniti.cil.itta.inventory.optical.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.WdmSystemSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WdmSystemSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(WdmSystemSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        WdmSystemSearchCriteria criteria = new WdmSystemSearchCriteria();
        criteria.setQ("  core ");
        assertThat(WdmSystemSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
