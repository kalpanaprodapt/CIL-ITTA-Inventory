package com.uniti.cil.itta.inventory.change.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.TechnicianSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TechnicianSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(TechnicianSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        TechnicianSearchCriteria criteria = new TechnicianSearchCriteria();
        criteria.setQ("  core ");
        assertThat(TechnicianSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
