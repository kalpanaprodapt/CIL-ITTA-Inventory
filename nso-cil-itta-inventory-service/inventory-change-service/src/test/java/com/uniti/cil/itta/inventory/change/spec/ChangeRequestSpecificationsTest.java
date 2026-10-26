package com.uniti.cil.itta.inventory.change.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.ChangeRequestSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChangeRequestSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ChangeRequestSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ChangeRequestSearchCriteria criteria = new ChangeRequestSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ChangeRequestSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
