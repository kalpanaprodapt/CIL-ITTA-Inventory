package com.uniti.cil.itta.inventory.change.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.FieldTaskSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FieldTaskSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(FieldTaskSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        FieldTaskSearchCriteria criteria = new FieldTaskSearchCriteria();
        criteria.setQ("  core ");
        assertThat(FieldTaskSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
