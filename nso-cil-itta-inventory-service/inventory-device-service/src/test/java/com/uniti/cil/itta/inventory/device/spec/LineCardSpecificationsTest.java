package com.uniti.cil.itta.inventory.device.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.LineCardSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LineCardSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(LineCardSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        LineCardSearchCriteria criteria = new LineCardSearchCriteria();
        criteria.setQ("  core ");
        assertThat(LineCardSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
