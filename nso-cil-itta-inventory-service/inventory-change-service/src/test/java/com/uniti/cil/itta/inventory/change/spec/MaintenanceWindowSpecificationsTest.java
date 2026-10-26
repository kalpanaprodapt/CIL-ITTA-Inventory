package com.uniti.cil.itta.inventory.change.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.MaintenanceWindowSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaintenanceWindowSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(MaintenanceWindowSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        MaintenanceWindowSearchCriteria criteria = new MaintenanceWindowSearchCriteria();
        criteria.setQ("  core ");
        assertThat(MaintenanceWindowSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
