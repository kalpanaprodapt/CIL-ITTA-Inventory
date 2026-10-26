package com.uniti.cil.itta.inventory.facility.spec;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.RoomSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoomSpecificationsTest {

    @Test
    void nullCriteriaStillScopesToTenant() {
        assertThat(RoomSpecifications.fromCriteria("t", null)).isNotNull();
    }

    @Test
    void freeTextBuildsSpecification() {
        RoomSearchCriteria criteria = new RoomSearchCriteria();
        criteria.setQ("  core ");
        assertThat(RoomSpecifications.fromCriteria("t", criteria)).isNotNull();
    }

}
