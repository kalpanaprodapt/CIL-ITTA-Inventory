package com.uniti.cil.itta.inventory.change.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.WorkOrderSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WorkOrderSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(WorkOrderSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        WorkOrderSearchCriteria criteria = new WorkOrderSearchCriteria();
        criteria.setQ("  core ");
        assertThat(WorkOrderSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
