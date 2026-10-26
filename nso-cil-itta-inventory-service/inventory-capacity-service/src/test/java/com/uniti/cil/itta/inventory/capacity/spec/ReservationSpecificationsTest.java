package com.uniti.cil.itta.inventory.capacity.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.ReservationSearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReservationSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(ReservationSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setQ("  core ");
        assertThat(ReservationSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
